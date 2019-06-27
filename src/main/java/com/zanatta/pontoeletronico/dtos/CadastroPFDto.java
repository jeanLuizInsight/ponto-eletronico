package com.zanatta.pontoeletronico.dtos;

import java.io.Serializable;
import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPFDto implements Serializable {

	private static final long serialVersionUID = 2640103295341989820L;

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	private String cnpj;

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "Nome é um campo obrigatório!")
	@Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres!")
	public String getNome() {
		return nome;
	}

	@NotEmpty(message = "E-mail é um campo obrigatório!")
	@Length(min = 5, max = 200, message = "Nome deve conter entre 5 e 200 caracteres!")
	@Email(message = "E-mail inválido!")
	public String getEmail() {
		return email;
	}

	@NotEmpty(message = "Senha é um campo obrigatório!")
	public String getSenha() {
		return senha;
	}

	@NotEmpty(message = "CPF é um campo obrigatório!")
	@CPF(message = "CPF inválido!")
	public String getCpf() {
		return cpf;
	}

	public Optional<String> getValorHora() {
		return valorHora;
	}

	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}

	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	@NotEmpty(message = "CNPJ é um campo obrigatório!")
	@CNPJ(message = "CNPJ inválido!")
	public String getCnpj() {
		return cnpj;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}

	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPFDto [nome=" + nome + ", email=" + email + ", cpf=" + cpf + ", cnpj=" + cnpj + "]";
	}

}
