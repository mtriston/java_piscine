import java.lang.reflect.*;

class User {
	public String firstName;
	public String lastName;
	public int height;

	public User() {
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.height = 0;
	}

	public int grow(int value) {
		this.height += value;
		return this.height;
	}
}

public class Program {
	public static void main(String[] args) {
		Class myClass = User.class;
		Field[] fields = myClass.getFields();
		for (Field field : fields) {
			System.out.print(field.getType().getSimpleName() + " ");
			System.out.println(field.getName());
		}

		Method[] methods = myClass.getDeclaredMethods();
		for (Method method : methods) {
			System.out.print(method.getReturnType() + " ");
			System.out.print(method.getName() + "(");
			Class<?>[] params = method.getParameterTypes();
			for (Class<?> param : params) {
				System.out.print(param.getName() + " ");
			}
			System.out.println(")");
		}

		Constructor constructor = myClass.getConstructor();

	}
}