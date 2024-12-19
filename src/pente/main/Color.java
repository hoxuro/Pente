package pente.main;

public enum Color {

	RED("\u001B[31m"), YELLOW("\033[33m"), RESET("\u001B[0m");

	private final String code;

	Color(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

}
