package com.zanatta.pontoeletronico.dtos;

import java.io.Serializable;

public class EmpresaDto implements Serializable {

	private static final long serialVersionUID = 4091476961581158038L;

	private Long id;
	private String razaoSocial;
	private String cnpj;

	public Long getId() {
		return id;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "EmpresaDto [razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}

}
