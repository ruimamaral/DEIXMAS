package pt.ulisboa.tecnico.rnl.dei.deixmas.exceptions;

public enum ErrorMessage {

	NO_SUCH_PARTICIPANT("Não existe nenhum participante com o ID %s", 1001),
	PARTICIPANT_NAME_NOT_VALID("O nome de participante especificado não é válido.", 1002);

	private final String label;
	private final int code;

	ErrorMessage(String label, int code) {
		this.label = label;
		this.code = code;
	}

	public String getLabel() {
		return this.label;
	}

	public int getCode() {
		return this.code;
	}
}
