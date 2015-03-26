# New Activities and Intents

## App UX

* App UI Mocks
* Pensando na UX (Experiência do Usuário) e Design Centrado no Usuário 

* ListItem Click Listener
  * ListView.setOnItemClickListener

## Toast
  * Pop up for a short message
  * Useful for debugging or visual status change without altering the whole UI
  * Toast.makeText(Context, String, lenght).show()

## Activities 
  * Creating Activites
    * Class
    * Declaração no manifesto
    * Por que Activity with Fragment
    * Activity Hierarchy
      * A raiz é a Activity principal e as "child" são activities de detalhes e outras.
      * Auxiliar à navegação
      * Up button sempre te levará um nível acima na hierarquia enquanto o local para onde o back button te leva pode variar de acordo com o histórico de telas


## Intents Framework
  
  * Utilizado para comunicação entre componentes da aplicação ou sistema para expecificar a ação que se deseja realizar
  * Um envelope contendo destino e uma pequena quantidade de dados que pode ser carreado com ele (Extras)
  * Explicity Intents: tem como alvo um componente explicitamente definidos
  * Implicit Intents: o nome do componente não é expecificado explicitamente, ao invés uma Action que se deseja realizar é definida e o sistema decide qual componente/aplica;cão irá executar a tarefa e com quais dados 
  * Common Intents: https://developer.android.com/design/patterns/settings.html
    * Launching an Activity
    * Intent Resolution
    * Share Intent
    * Broadcast Intent
    * Intent Filters

### Broadcasts Intents 
  
* Envia mensagens para BroadcastReceivers que utiliza IntentFilters para receber as mensagens corretas
* Manifest and Dynamic defined receivers
    
## Preferences

  * Settings Design:
    * não torne tudo uma configuração!
    * quanto menos opções melhor, é sempre mais fácil incluir opções do que removê-las depois
    * Flow para criar uma setting: https://developer.android.com/design/patterns/settings.html 
  * Utilize os tipos comuns de preferences
  * É salvo no arquivo default de SharedPreferences 
  * PreferenceActivity (até Gingerbread) vs FragmentActivity (Honeycomb e mais atuais)
  * Utilize o Wizard do Android Studio
  * pref_general.xml 
  * PreferenceScreen é a raiz do layout das preferences
    * Title, Key, Default Value
    * Declarar propriedades como constantes no strings.xml
    * Não traduzir keys! "translatable=false" como propriedade da String
    * Adicionar o xml de preferencias ao Fragment/Activity de settings com o método addPreferencesFromResources

## SharedPreferences
  
  * O que são?
  * Lendo SharedPreferences
    * PreferenceManager.getDefaultSharedPreferences(Context)
    * PreferenceManaget.getXXX

## Debugging
	
## ShareActionProvider: 

* Can share anything!
* onCreateOptionsMenu

	


