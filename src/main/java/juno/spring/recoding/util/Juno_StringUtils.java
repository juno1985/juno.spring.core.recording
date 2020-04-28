package juno.spring.recoding.util;


public class Juno_StringUtils {
	
	private static final String FOLDER_SEPARATOR = "/";

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\";
	
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	public static String cleanPath(String path) {
		if(!hasLength(path)) return path;
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
		/**
		 * 还需要考虑网络文件资源如file://.......
		 * to be done
		 */
		return pathToUse;
	}

	private static String replace(String path, String windowsFolderSeparator, String folderSeparator) {
		
		int index = path.indexOf(windowsFolderSeparator);
		//如果路径里没有windows目录分隔符 \\
		if(index == -1) {
			return path;
		}
		/**
		 * 如果有windows分隔符则需要替换/
		 * 使用StringBuilder ...
		 * to be done
		 */
		
		
		return null;
	}
}
