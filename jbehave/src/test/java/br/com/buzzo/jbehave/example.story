

Cenário: Novo registro 

Dado que o usuario esta na tela de cadastro
Quando ele digita corretamente o nome
Quando ele digita corretamente o email
Quando ele digita corretamente o numero do telefone
Quando ele pressiona o botao de registro
Então o cadastro eh realizado com sucesso




Cenário: Não é possível registrar um cliente com um e-mail previamente cadastrado em outro registro

Dado que ja existe um cliente registrado
Dado que o usuario esta na tela de cadastro
Quando ele digita corretamente o nome
Quando preenche o email de um cliente ja registrado
Quando ele digita corretamente o numero do telefone
Quando ele pressiona o botao de registro
Então a mensagem de email ja registrado eh mostrada
