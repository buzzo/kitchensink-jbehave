package br.com.buzzo.jbehave;

import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.web.selenium.WebDriverProvider;
import org.junit.Assert;

import br.com.buzzo.jbehave.model.Member;
import br.com.buzzo.jbehave.util.DatabaseUtil;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;

public class ExampleSteps {

	private final RegisterPage home;

	public ExampleSteps(final WebDriverProvider webdriver) {
		this.home = new RegisterPage(webdriver);
	}

	static {
		Fixture.of(Member.class).addTemplate("default", new Rule() {
			{
				add("id", random(Long.class, range(1L, 200L)));
				add("name", random("Leonardo da Silva", "Gustavo Gongo"));
				add("email", "member@gmail.com");
				add("phoneNumber", "12345678901");
			}
		});
	}

	@BeforeScenario
	public void before() throws Exception {
		DatabaseUtil.clearDatabase();
	}

	@Given("ja existe um cliente registrado")
	public void givenJaExisteUmClienteRegistrado() throws Exception {
		final Member member = Fixture.from(Member.class).gimme("default");
		DatabaseUtil.save(member);
	}

	@When("preenche o email de um cliente ja registrado")
	public void whenPreencheOEmailDeUmClienteJaRegistrado() {
		this.home.type_email("member@gmail.com");
	}

	@Then("a mensagem de email ja registrado eh mostrada")
	public void thenAMensagemDeEmailJaRegistradoEhMostrada() {
		Assert.assertTrue(
				"Mensagem de email duplicado nao encontrada.",
				this.home.getRegisterMessage().contains(
						"Unique index or primary key violation"));
	}

	@Given("o usuario esta na tela de cadastro")
	public void givenOUsuarioEstaNaTelaDeCadastro() {
		this.home.open();
	}

	@When("ele digita corretamente o nome")
	public void whenEleDigitaCorretamenteONome() {
		this.home.type_name("Andre");
	}

	@When("ele digita corretamente o email")
	public void whenEleDigitaCorretamenteOEmail() {
		this.home.type_email("andre@buzzo.com.br");
	}

	@When("ele digita corretamente o numero do telefone")
	public void whenEleDigitaCorretamenteONumeroDoTelefone() {
		this.home.type_phone("12345678901");
	}

	@When("ele pressiona o botao de registro")
	public void whenElePressionaOBotaoDeRegistro() {
		this.home.register();
	}

	@Then("o cadastro eh realizado com sucesso")
	public void thenOCadastroEhRealizadoComSucesso() {
		Assert.assertEquals("Mensagem de sucesso de cadastro invalida",
				"Registered!", this.home.getRegisterMessage());
	}

}
