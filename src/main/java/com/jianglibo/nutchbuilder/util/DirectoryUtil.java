package com.jianglibo.nutchbuilder.util;


import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectoryUtil {
	
	private static Logger log = LoggerFactory.getLogger(DirectoryUtil.class);

	public static boolean deleteRecursiveIgnoreFailed(Path path) throws FileNotFoundException{
		if (path == null) {
			return true;
		}
		return deleteRecursiveIgnoreFailed(path.toFile());
	}
	
    public static boolean deleteRecursiveIgnoreFailed(File path) throws FileNotFoundException{
        if (!path.exists()) {
        	return true;
        }
        boolean ret = true;
        if (path.isDirectory()) {
            for (File f : path.listFiles()){
                ret = DirectoryUtil.deleteRecursiveIgnoreFailed(f);
            }
        }
        ret =  path.delete(); // even if path is a directory, it's empty now, so we can delete it.
    	if (!ret) {
    		log.error("cannot delete file {} in unjarfolder.", path.getAbsolutePath());
    	}
    	return ret;
    }
    
    /**
     * 
     * @param wholePath
     * @param startsWith
     * @return longest match path.
     */
    public static Path findMiddlePathStartsWith(Path wholePath, String startsWith) {
		Path p = wholePath.toAbsolutePath().normalize();
		Path newp = p.getRoot();
		Path mostfarPath = null;
		int pcount = p.getNameCount();
		for(int i = 0; i< pcount; i++) {
			newp = newp.resolve(p.getName(i));
			if (newp.getFileName().toString().startsWith(startsWith)) {
				mostfarPath = newp;
			}
		}
		return mostfarPath;
    }
}

