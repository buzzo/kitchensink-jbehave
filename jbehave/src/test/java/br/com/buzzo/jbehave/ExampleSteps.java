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

    private final HomePage home;

    public ExampleSteps(final WebDriverProvider webdriver) {
        this.home = new HomePage(webdriver);
    }

    @When("preenche corretamente o telefone")
    public void andPreencheCorretamenteOTelefone() {
        this.home.type_phone("123456789012");
    }

    @When("pressiona o botao registrar")
    public void andPressionaOBotãoRegistrar() {
        this.home.register();
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

    @Given("o cliente esta na tela de registro")
    public void givenOClienteEstaNaTelaDeRegistro() {
        this.home.open();
    }

    @Then("o cadastro eh realizado com sucesso")
    public void thenOCadastroÉRealizadoComSucesso() {
        Assert.assertEquals("Mensagem de sucesso de cadastro invalida", "Registered!", this.home.getRegisterMessage());
    }

    @When("preenche corretamente o email")
    public void whenPreencheCorretamenteOEmail() {
        this.home.type_email("andre@buzzo.com.br");
    }

    @When("preenche corretamente o nome")
    public void whenPreencheCorretamenteONome() {
        this.home.type_name("andre");
    }
}
