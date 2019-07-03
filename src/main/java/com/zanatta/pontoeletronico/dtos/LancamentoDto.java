package com.zanatta.pontoeletronico.dtos;

import java.io.Serializable;
import java.util.Optional;
import javax.validation.constraints.NotEmpty;

public class LancamentoDto implements Serializable {

	private static final long serialVersionUID = -959144329417298642L;

	private Optional<Long> id = Optional.empty();
	private String data;
	private String descricao;
	private String localizacao;
	private String tipo;
	private Long funcionarioId;

	public Optional<Long> getId() {
		return id;
	}

	@NotEmpty(message = "Data é obrigatória!")
	public String getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public String getTipo() {
		return tipo;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setId(Optional<Long> id) {
		this.id = id;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	@Override
	public String toString() {
		return "LancamentoDto [id=" + id + ", data=" + data + ", tipo=" + tipo + ", funcionarioId=" + funcionarioId + "]";
	}

}
