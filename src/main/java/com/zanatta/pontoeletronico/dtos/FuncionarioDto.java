package com.zanatta.pontoeletronico.dtos;

import java.io.Serializable;
import java.util.Optional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class FuncionarioDto implements Serializable {

	private static final long serialVersionUID = 5947014315511723735L;

	private Long id;
	private String nome;
	private String email;
	private Optional<String> senha = Optional.empty();
	private Optional<String> cpf = Optional.empty();
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();

	public Long getId() {
		return id;
	}

	@NotEmpty(message = "Nome é obrigatório!")
	@Length(min = 5, max = 200, message = "Nome deve conter entre 3 e 200 caracteres!")
	public String getNome() {
		return nome;
	}

	@NotEmpty(message = "E-mail é obrigatório!")
	@Length(min = 5, max = 200, message = "E-mail deve conter entre 5 e 200 caracteres!")
	@Email(message = "E-mail inválido!")
	public String getEmail() {
		return email;
	}

	public Optional<String> getSenha() {
		return senha;
	}

	public Optional<String> getCpf() {
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(Optional<String> senha) {
		this.senha = senha;
	}

	public void setCpf(Optional<String> cpf) {
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

	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", cpf=" + cpf + "]";
	}

}
