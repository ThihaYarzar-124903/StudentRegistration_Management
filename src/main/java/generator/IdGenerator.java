package generator;

public class IdGenerator {
	public static String generateUserCode(int lastId) {
		 lastId++;
		 String userCode = "USR" + String.format("%04d", lastId);
		 return userCode;
	}
	
	 public static String generateStudentCode(int lastId) {
		 lastId++;
		 String studentCode = "STU" + String.format("%04d", lastId);
		 return studentCode;
	}
	 
	 public static String generateCourseCode(int lastCourseId) {
		 lastCourseId++;
		 String courseCode = "CUR" + String.format("%03d", lastCourseId);
		 return courseCode;
	}
	 
}
