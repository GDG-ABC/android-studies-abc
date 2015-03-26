# Services and Notifications

## Tarefas em Background

* A utilização de um AsyncTask atrelado à um componente de UI não é a melhor forma de realizar tarefas em background. Principalmente porque quando a Activity morre a thread permanece ativa até a conclusão de sua tarefa, o que pode ocasionar diversas threads penduradas toda vez que a activity é destruída e contruída.
* É importante ter uma uma estratégia para sincronizar os dados quando o usuário está interagindo com a app, mas também quando a app está esperando para ser usada. Como sincronizar dados de maneira eficiente sem desperdiçar recursos do telefone (e.g.: bateria)?

### Services

* É o componente padrão do Android para rodar tarefas em background.
* Não possuem uma UI atreladas à eles.
* São ativados com o método startService(intent)
* São principais event handlers são:
  * onCreate
  * onStartCommand
  * onBind
  * onDestroy
* Um service roda com a mesma prioridade de uma Activity visível portanto é muito difícil que o sistema o mate para liberar recursos.
* Uma outra alternativa é mover o Service para foreground, desta forma o Service terá a mesma prioridade de uma Activity ativa e o o Android nunca a matará. É importante usar isso com cautela e apenas quando realmente necessário pois isso pode impactar significativamente o gerenciamento de recursos no Android. Isso pode ser feito chamando o método startForeground(Notification) logo no onStartCommand.
* Rodam na Main Thread portanto é fundamental criar uma thread em background para executar as tarefas.
* Existe um componente padrão chamado IntentService que possui uma fila de processamento e uma thread de processamento. Basta implementar o event handler onHandleIntent.
* Geralmente a criação de um serviço customizado pode ser substituído pelo uso de um IntentService (tarefas em background) ou SyncAdapter (sincronização de dados).


## App Priority
	
* As três leis do gerenciamento de recursos no Android
  * Android deverá garantir uma UX suave
  * Android irá manter Activitites e todos os Services rodando, a menos que isso viole a primeira lei
  * Android irá manter todas as apps em background rodando, a menos que isso viole a primeira ou segunda lei.


## AlarmManager 
		
* Permite você dizer ao sistema que você gostaria de chamar um componente da sua aplicação após um período de tempo para fazer algum processamento em background.
* Pode fazer chamadas periódicas.
* Alarmes disparam PendingIntents como forma de callback para as aplicações que configuraram o Alarme.
* O PendingIntent permite ao componente que o está utilizando enviar dados com a mesma identidade e permissão do aplicativo que o criou. Isso permite ao Android chamar sua aplicação de volta de maneira assíncrona e segura.
* https://developer.android.com/training/scheduling/alarms.html#set
* http://developer.android.com/reference/android/app/PendingIntent.html 

## BroadcastReceiver 
		
* Projetado para receber Intents transmitidos pelo sistema ou outras aplicações.
* Registra IntentFilters para os broadcasts que deseja receber.
* É um forma de fazer a aplicação receber intents disparados por Alarmes.
* Tratamento é implementado no onReceive.
* Precisa ser registrado no Manifest.
* http://developer.android.com/reference/android/content/BroadcastReceiver.html 

## Transferindo Dados Eficientemente

* O modem é um dos componentes que mais drena energia da bateria do celular.
* Ser mais eficiente significa passar menos tempo transferindo menos dados, reduzindo o tamanho dos pacotes tranferidos e efetuar sincronizações em frequências menores.
* The Cookie Droid Conundrum
  * Menos downloads com cargas maiores de dados ou downloads mais frequentes com cargas menores de dados?
* The Celular Radio State machine
* Dicas:
  * Minimize a transição entre estados do modem.
  * Busque e faça de cache antecipadamente de pelo menos 2~5min de dados (1-5mb) de dados.
  * Agrupe tranferências que não tenham criticidade temporal.
  * Agrupe tranferências que tenham sensibilidade temporal.
  * Lembre-se que qualquer tranferência deixará o rádio do telefone ativo por pelo menos 30s
  * É importante achar o equilíbrio entre os dois modelos.

## SyncAdapters

* SyncManager framework para boas práticas de sincronização de dados.
* O SyncManager agrupa, agenda e executa solicitações de rede de diversas apps para fazer melhor uso dos recursos do telefone. 
* Peças:
  * Authenticator: gerencia a autenticação com serviços de background que serão sincronizados.
  * AuthenticatorService: Permite ao sincronization framework acessar o authenticator.
  * SyncService: Possibilita acesso ao SyncAdapter.
  * SyncAdapter: Implementação da sincrnização. Uma classe utilitária é a AbstractThreadedSyncAdapter.
  * onPerformSync: executa a sincronização.
  * XML Configuration
    * authenticator.xml: pluga o autenticador com o SyncAdapter utilizando meta dados. 
    * syncadapter.xml: configurações do SyncAdapter. 
    * strings.xml: adicionar o content_authority e o tipo de conta para sincronização.
  * Manifest Configuration 
    * Adicionar os AuthenticatorService e SyncService 
    * Tornar o provider a ser sincronizado syncable com a tag android:syncable="true"
    * Adicionar permissões de sincronização: android.permission.READ_SYNC_SETTINGS, android.permission.WRITE_SYNC_SETTINGS e android.permission.AUTHENTICATE_ACCOUNTS
* Rodando o SyncAdapter: http://developer.android.com/training/sync-adapters/running-sync-adapter.html 
  * Quando os dados do servidor mudarem
  * Quando os dados no device mudarem
  * Quando o sistema enviar uma mensagem pela rede
  * Em intervalos regulares
  * Sob demanda
  
## Google Cloud Messaging

* http://developer.android.com/google/gcm/index.html 
* Permite ao servidor notificar sua app que dados estão disponíveis para sincronização, o famoso push message.

## Notifications

* http://developer.android.com/guide/topics/ui/notifiers/notifications.html
* Design Guide: http://developer.android.com/design/patterns/notifications.html  
* É uma mensagem que pode ser mostrada ao usuário fora da UI normal da sua aplicação.
* Geralmente elas aparecem na barra de notificação. Mais recentemente no Lollipop é possível gerar notificações na lockscreen e notificações heads up para quando alguma app está em full screen.
* Para criar uma notificação basta usar o factory method em NotificationCompat.Builder.build() que irá retornar um objeto Notification.
* Um objeto Notification deve conter ao menos o smallIcon, ContentTitle, ContentText
* Existem outros conteúdos adicionais que podem ser adicionados como botões de ação, icones, e agrupamento de notificações.
* Para enviar a notificação ao usuário utilizar o método NotificationManager.notify()
* Uma notificação pode conter um PendingIntent para retornar uma ação a sua aplicação.
* TaskStackBuilder: http://developer.android.com/reference/android/support/v4/app/TaskStackBuilder.html
  * Permite a criação de um backstack artificial para quando sua app é acessada a partir de um PendingIntent, viabilizando uma navigação via back button mesmo se o Intent levou o usuário a pontos mais profundos da app.
  