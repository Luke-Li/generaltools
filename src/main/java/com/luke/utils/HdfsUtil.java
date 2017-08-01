package com.luke.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * file operation on HDFS
 * 
 */
public class HdfsUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HdfsUtil.class);
	// read the file from HDFS
	public void readFile(Configuration conf, String fileName) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			FSDataInputStream dis = hdfs.open(new Path(fileName));
			IOUtils.copyBytes(dis, System.out, 4096, false);
			dis.close();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
		}
	}

	// copy the file from HDFS to local
	public void getFile(Configuration conf, String srcFile, String dstFile) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path srcPath = new Path(srcFile);
			Path dstPath = new Path(dstFile);
			hdfs.copyToLocalFile(true, srcPath, dstPath);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	// copy the local file to HDFS
	public void putFile(Configuration conf, String srcFile, String dstFile) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path srcPath = new Path(srcFile);
			Path dstPath = new Path(dstFile);
			hdfs.copyFromLocalFile(srcPath, dstPath);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	// create the new file
	public FSDataOutputStream createFile(Configuration conf, String fileName) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(fileName);
			FSDataOutputStream outputStream = hdfs.create(path);
			return outputStream;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// rename the file name
	public boolean reNameFile(Configuration conf, String srcName, String dstName) {
		try {
			Configuration config = new Configuration();
			FileSystem hdfs = FileSystem.get(config);
			Path fromPath = new Path(srcName);
			Path toPath = new Path(dstName);
			boolean isRenamed = hdfs.rename(fromPath, toPath);
			return isRenamed;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	// delete the file
	// tyep = true, delete the directory
	// type = false, delete the file
	public boolean delFile(Configuration conf, String fileName, boolean type) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(fileName);
			boolean isDeleted = hdfs.delete(path, type);
			return isDeleted;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	// Get HDFS file last modification time
	public long getFileModTime(Configuration conf, String fileName) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(fileName);
			FileStatus fileStatus = hdfs.getFileStatus(path);
			long modificationTime = fileStatus.getModificationTime();
			return modificationTime;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	// check if a file exists in HDFS
	public boolean checkFileExist(Configuration conf, String fileName) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(fileName);
			boolean isExists = hdfs.exists(path);
			return isExists;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	public boolean createDirectory(Configuration conf, String dirName) {
		try {
			FileSystem hdfs = FileSystem.get(conf);
			Path path = new Path(dirName);
			boolean created = hdfs.mkdirs(path);
			return created;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return false;
	}

	// Get the locations of a file in the HDFS cluster
	public List<String[]> getFileBolckHost(Configuration conf, String fileName) {
		try {
			List<String[]> list = new ArrayList<String[]>();
			FileSystem hdfs = FileSystem.get(conf);			
			Path path = new Path(fileName);
			FileStatus fileStatus = hdfs.getFileStatus(path);

			BlockLocation[] blkLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());

			int blkCount = blkLocations.length;
			for (int i = 0; i < blkCount; i++) {
				String[] hosts = blkLocations[i].getHosts();
				list.add(hosts);
			}
			return list;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			//e.printStackTrace();
		}
		return null;
	}

	// Get a list of all the nodes host names in the HDFS cluster
	// have no authorization to do this operation
	public String[] getAllNodeName(Configuration conf) {
		try {
			FileSystem fs = FileSystem.get(conf);
			DistributedFileSystem hdfs = (DistributedFileSystem) fs;
			DatanodeInfo[] dataNodeStats = hdfs.getDataNodeStats();
			String[] names = new String[dataNodeStats.length];
			for (int i = 0; i < dataNodeStats.length; i++) {
				names[i] = dataNodeStats[i].getHostName();
			}
			return names;
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			//System.out.println("error!!!!");
			//e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		HdfsUtil hdfsFile = new HdfsUtil();

		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://hadoop-namenode1:8020");
		String[] nodes = hdfsFile.getAllNodeName(conf);
		for (String node : nodes) {
			System.out.println(node);
		}
	}
}