package controls;

public class CallBackUtils {

	
	/**
	 * Checks if the provided Object implements the given method and argument
	 * @param object - Object
	 * @param methodName - String
	 * @param classe - Class
	 * @return boolean
	 */
	public static boolean checkRequiredCallback(Object object, String methodName, Class<?> classe) {
		
		try {
			object.getClass().getMethod(methodName, classe);
		} catch (SecurityException e) {
			System.out.println("Cannot access methods of "+object.getClass().getName());
			return false;
		} catch (NoSuchMethodException e) {
			System.out.println(object.getClass().getName()+" must implement this method: public void "+methodName+"("+classe.getCanonicalName()+")");
			return false;
		}
		return true;
	}
}
