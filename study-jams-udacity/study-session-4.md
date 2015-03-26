# Lesson 4a: Activity Lifecycle and Data

## Backstack

## Lifecycle
  
* O celular tem recursos limitados, o backstack mata aplicações em background com baixa prioridade.
* O Android gerencia isso, sua app pode morrer a qualquer momento e deve estar pronta para isso!
* _onCreate_: cria e conecta toda a UI
* _onStart_: chamado pouco antes de a Activity se tornar visivel
* _onResume_: chamado pouco antes de a Activity receber foco e se tornar ativa
* _onPause_: Activity perdeu o foco, como quando uma caixa de dialogo é aberta obscurecendo parte da Activity. Não parar rotinas que atualizem a UI pois ela ainda está visivel.
* _onStop_: Activity foi escondida, a app é totalmente obscurecida por outra app
* _onDestroy_: fim do lifecycle da app
* Quando a orientação da app muda a Activity é destruída e recriada.
* Estar pronto para destruição no onPause, os estados após este podem não ser executados pelo Android (exceto a partir do Honeycomb que o onStop também é garantido). Neste estado deve-se liberar recursos que estavam sendo utilizados, como sockets, arquivos, sensores, gps etc.
* Entendeu poque botões de sair não servem pra nada no Android? Você poderia chamar finish() em uma Activity mas isso teria o mesmo efeito de tocar em voltar.... 
  * https://www.youtube.com/watch?v=631T7B8HOv4
* Quando a app vai para background temos que prepará-la para voltar ao estado que estava quando o usuário a deixou: 
  * _onSaveInstanceState_: chamado antes do onPause pode ser usado para salvar o estado da aplicação
  * _onRestoreInstanceState_: chamado logo após o onCreate, SOMENTE se a app foi reiniciada após ser destruída pelo sistema

## Storage 

* SharedPreferences: framework comum para armazenar dados do tipo chave e valor.
* SQLiteDatabase: Banco de dados relacional.
  * SQLite Tutorial: https://www.udacity.com/course/viewer#!/c-ud853/l-3621368730/m-2602608541
  * Android Data Storage Guide: http://developer.android.com/guide/topics/data/index.html 
  * Contract: Acordo entre o modelo de dados e as views descrevendo como as informações são acessadas.
  * Foreign keys
  * Base Columns: http://developer.android.com/reference/android/provider/BaseColumns.html
  * SQL Join: http://en.wikipedia.org/wiki/Join_(SQL)#Inner_join
  * Content Providers: http://developer.android.com/guide/topics/providers/content-providers.html
  * SqliteOpenHelper: funcionalidades para criar a base de dados e gerenciar diversas versões de base de dados
    * Versoes e nome do db
    * _onCreate_: chamado na primeira vez que a base é usada para criação da estrutura do banco de dados
    * _onUpgrade_: chamado quando a base já existe para efetuar possíveis atualizações na estrutura da base
    * ContentValues
    * SQLiteDatabase: 
      * _query()_
      * Cursor: estrutura de controle para iterar pelos resultados da busca
   		* Não esquecer de fechar o cursor e a database

## Testes Unitários

* JUnit: http://www.tutorialspoint.com/junit/junit_quick_guide.htm
* Tests no Android: http://developer.android.com/tools/testing/testing_android.html 
* AndroidTestCase
  * _setUp_ and _tearDown_
  * _assert_
  * _testSuite_: cria um pacote com todos os testes disponíves para serem rodados.

# Lesson 4b: Content Providers

## Content Providers: 

* Possibilitar uma ótima experiência ao usuário mesmo quando device estiver offiline.
* Permite compartilhar dados de modo confiável entre apps e abstraindo a origem dos dados e a forma como eles são armazenados.
* Você poderia não fazer, mas faça! Muito mais fácil de trocar o data source se necessário e mais simples para alguém conectar os dados na UI
* Widgets e Search também usam ContentProviders
* SyncAdapter e CursorLoader utilizam content providers para atualizar a UI de maneira mais eficiente
* Criando um content provider: 
  * How to use a ContentProvider: https://www.udacity.com/course/ud258
  * URI 
    * Podem ser passadas entre activites com o método setData do Intent e recuperado com getDataString()
    * Estrutura
      * scheme
      * authority
      * location
      * query
      * appendPath, appendId, appendQueryParameter
      * Definidas no contrato para torná-las acessíveis
  * UriMatcher: Faz o match da URI passada para ele com um dos padrões de URI registrados anteriormente, é utilizado para resolver qual a tarefa a ser realizada pelo ContentProvider 
    * Geralmente cada padrão/tipo de URI é relacionado a um id definido como uma constante na classe do ContentProvider
    * definir o URIMatcher em um bloco estatico para ser inicializado junto com a app
    * adicionar as URI que se deseja fazer match
  * Registrar o ContentProvider no Manifest: <provider android:authority="" android:name=""></provider>
  * Implementar os métodos do ContentProvider que efetivamente manipulam os dados:
  
´´´java  
...

public boolean onCreate() {...}

public Cursor query(Uri, String[], String, String[], String) {...} // uri, projection, selection, selectionArgs, sortOrder

public Uri insert(Uri, ContentValues) {...}

public int update(Uri, ContentValues, String, String[]) {...}

public int delete(Uri, String, String[]) {...}

public String getType(Uri) {...}

...
´´´

  * ContentProvider Types  
    * Dir: um conjunto de registros
    * Item: um único registro
    * Poderiar retornar um outro mime type como JPEG caso o ContentProvider retornasse imagens para uma determinada URI
  * Para cada método usar o URIMatcher para identificar qual operação executar ele retornará o id da operação que pode ser utilizado em um switch para escolher qual operação executar.
  * Usar o DatabaseOpenHelper para obter um readable database para executar as operações
  * SQLiteQueryBuilder: suporta a construção de queries 
    * usar setTables para definir o Join
    * usar placeholders '?' para definir pontos onde serão inseridos parametros
  * Notificação dos Cursores
    * NotifyForDescendance: quando a URI root for notificada de uma alteração, todas as URIs filhas também serão notificadas
    * Nunca se esquecer **getContext().getContentResolver().notifyChange(uri, null)**
* Otimização de Inserts com o BulkInsert: 
  * usado para inserir múltiplos registros de uma única vez
  * um loop que executa um insert para cada ContentValues passado
  * utilizar transactions para garantir que os registros serão inseridos ou cancelados juntos se operação falhar e de maneira mais eficiente
* Exportando ContentProviders: para torná-lo acessivel por outras aplicações
  * android:exported="true"
  * é possível requisitar permissões especiais para limitar quais apps terão acesso ao seu ContentProvider
  * a permissão é especificada na tag <application>
  * e configurada na tag <provider android:permission="com.myapp.LICENSE_TO_KILL">
  * <permission android:name="com.myapp.LICENSE_TO_KILL" android:protectionLevels="dangerous" android:label="Licensed to Kill">
* Native ContentProviders:
  * http://developer.android.com/reference/android/provider/package-summary.html
  * http://developer.android.com/guide/topics/providers/contacts-provider.html
  * http://developer.android.com/guide/topics/providers/calendar-provider.html 

# Lesson 4c: Hooking it up with Loaders

## Loaders
		
* Possibilidade o carregando de dados de maneira assíncrona para atualizar a UI
* Quando ele é criado uma AsyncTask é criada internamente para carregar os dados em background
* Ele também é configurado para monitorar qualquer alteração que ocorra nos dados e notificar a UI
* O CursorLoader é utilizado para manter sincronizado uma URI do ContentProvider com a UI
* Você pode implementar seu proprio Loader extendendo o AsyncTaskLoader: http://developer.android.com/reference/android/content/AsyncTaskLoader.html 
* AsyncTask vs AsynctaskLoader
  * Basicamente a diferença esta no ciclo de vida
  * No caso do AsyncTask se a Activity é destruida ele é criado novamente consumindo recursos da aplicação (lembrando que ele havia sido criado no onCreate da Activity)
  * Já o AsyncTaskLoader é inicializado e reinicializado se conectando sempre ao mesmo AsyncTask
  * O CursorLoarder tem comportamento similiar mas é otimizado para retornar mais rapidamente caso os dados já estejam atualizados
* Querying Data from the Database:
  * O CursorLoader cria a URI que irá utilizar para obter os dados
  * O CursorLoader passa a URI para o ContentResolver que a repassará ao ContentProvider
  * O ContentProvider utiliza o DatabaseHelper para obter uma instancia da base de dados para manipulação
  * O ContentProvider passa a query que será executa ao SQLite
  * O SQLite executa a query e retorna as informações
  * O Android gera o cursor contendo os dados que viajam de volta até o CursorLoader
  * O CursorLoader finalmente passa o cursor a um CursorAdapter que irá se encarregar de popular a UI com aquelas informações
* Criando um CursorLoader
  * Crie um LoaderId
  * uma constante que identifica o Loader
  * uma Activity pode utilizar múltiplos Loader, e cada um é identificado por um id diferente
  * Preencha os callbacks do Loader
    * _Loader<Cursor> onCreateLoader(int i, Bundle bundle)_
      * onde o Loader é criado.
      * o Loader recebe os parametros padrao para acesso ao ContentProvider
      * ele se encarregara de chamar o ContentProvider ao ser executado pelo LoaderManager
    * _void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)_
      * Chamado quando o Loader termina e os dados estão prontos
      * trocamos o cursor no Adapter com o swapCursor
      * neste ponto atulizações na UI devem ser realizadas se necessário
    * _void onLoaderReset(Loader<Cursor> cursorLoader)_
      * Chamado quando o Loader é destruido
      * remova as referencias para os dados daquele Loader
      * no caso do Adapter chamar swapCursor(null) para limpar a referencia para aquele Cursor
* Inicialize o Loader com um LoadManager
  * getLoaderManager().iniLoader([loader id], [Bundle], [LoaderCallback]
  * Quando utilizado em um fragment o Loader deve ser inicializado no callback onActivityCreated
* Projections: array com as colunas que gostariamos que o ContentProvider retorne
  * mais eficiente pois obtem apenas os dados que serão utilizados
  * o banco de dados sempre retorna os dados na ordem que especificamos, podemos portanto criar constantes com indices na mesma ordem que a projection foi criada assim evitamos ter que ficar chamando o método getColumnIndex
  * No caso de joins é necessário expecificar o id explicitamente já que o campo pode ter o mesmo nome em várias tabelas

## Adapters:

* Utilizados para ligar os dados a componentes de UI
* _newView()_: retorna a iew que será populada, um layout pode ser inflado por exemplo
* _bindView()_: recebe a View criada no método newView() e o popula com os dados do Cursor


