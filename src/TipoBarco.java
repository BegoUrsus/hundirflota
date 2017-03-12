



public enum TipoBarco {
	PORTAVIONES(5, "Portaaviones"),
	ACORAZADO(4, "Acorazado"),
	DESTRUCTOR(3, "Destructor"),
	SUBMARINO(2, "Submarino");
	
	int longitud;
	String nombre;
	
	TipoBarco(int tam, String nom) {
		longitud = tam;
		nombre = nom;
	}

	/**
	 * @return the longitud
	 */
	public int getLongitud() {
		return longitud;
	}

	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return nombre;
	}
	

}
