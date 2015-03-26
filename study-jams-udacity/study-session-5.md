## Rich and Responsive Layouts

## Great Android Apps Design 

* Principles
  * Enchant-me
  * Simplify my Life
  * Make me Amazing

* Usuários avaliam as apps nos primeiros 30segundos
* Os usuários acabam avaliando a beleza da app inconscientemente:
  * é elegante?
  * profissional?
  * o quão fácil é utilizar?
* Diverdida de usar
* Surpreender o usuário de diversas formas com animações e cores
* Usar imagens ao invés de palavras
* Crie algo que funcione como mágica
* Atalhos para tarefas complexas
* o look and feel devem estar encaixados com a plataforma

* Quais suas apps favoritas e por que?
* Para costruir UIs utilizamos Views
* UI Building Blocks: http://developer.android.com/design/building-blocks/index.html 
* ViewGroups: container para outras views
  * **FrameLayout**
  * **LinearLayout**
    * Weight: permite distribuir a altura e a largura entre as Views contindas no ViewGroup
  * **RelativeLayout**
  * **GridLayout**
* Toda View precisa de uma altura e largura
  * wrap_content
  * match_parent
* Gravity: 
  * gravity: define o posicionamento do conteúdo da View
  * layout_gravity: define o posicionamento da View em relação ao componente pai
* Padding e Margin: 
  * padding: espaçamento entre as bordas da View e seu conteúdo
  * layout_margin: espaçamento entre as bordas do layout/view pai e as bordas da View filha
* Visibility: Visible, Invisible, Gone

* Criando a UI da sua App
  * Comece com os Wireframes e o fluxo da Aplicação!
  * Para criar os layouts quebre os Wireframes em pequenas partes até identificar as possbilidades de Utilizar os ViewGroups básicos.
  * Utilize textos em hardcode para verificar se a tela está sendo renderizada corretamente.

* layout_width=0dp and layout_weight=1: significa que quaquer espaço em branco não tomado por outras Views será distribuído entre Views que tenham pesos designados a elas
* Utilizando adapters para fazer o binding dos dados com a UI
  * CursorAdapter
  * Item View Types: o adapter pode ter multiplos tipo de item para criar layouts diferentes conforme necessário
  * View Holder Pattern: contem variáveis membras que referenciam as Views em um layout é armazenado no tag field da View. É mais fácil e mais rápido obter referências para os componentes da UI evitando o tempo de busca do findViewById sendo executado de novo para cada campo em cada View e em cada iem da lista.

* Formatting Strings
  * http://developer.android.com/guide/topics/resources/string-resource.html#FormattingAndStyling
  * https://developer.android.com/distribute/tools/localization-checklist.html
  * <string name="welcome">Hi <xliff:g id="name">%1$s</xliff:g>! You have <xliff:g id="count">%2$d</xliff:g> new messages.</string>
  * utilizar códigos unicode no strings.xml para caracteres especiais
  * context.getString(id_da_string, [parametros de formatação]

* Armazenar referências para componentes de tela como variáveis membras durante o onCreateView de um Fragment pode ajudar a ganhar performance e evitar muitos findViewById.
* Inflar layouts complexos pode utilizar muitos recursos e impactar a performance o app
  * Mantenha o layout raso e largo ao invés de fundo e curto. Mais irmãos são melhores do que muitos filhos.
  * Uma hierarquia de maneira geral não deve conter mais do que 10 views aninhadas ou mais de 80 views no total
  * Evite usar o FrameLayout como componente root na View se os filhos podem ser inseridos como filhas de outro layout, utilize no lugar dele a tag <merge> que é removido quando o layout for colocado no layout das Views pai.
* HierarchyViewer
* Lint
* Responsive Layout
  * Projetar a aplicação tendo em mente que ela será usada em devices com diferentes tamanhos, resoluções e densidades de tela.
  * É uma boa prática pensar em design responsivo do começo. É uma má prática pensar apenas no design para telefone e sóde epois de tudo pronto começar a imaginar como seria a versão para tablets, o layout do tablet geralmente impactará o layout do telefone e as decisões de projeto para criação das telas.
  * http://developer.android.com/distribute/essentials/quality/tablets.html
  * https://developer.android.com/design/patterns/multi-pane-layouts.html
  * Android Design in Action: Responsive Design https://www.youtube.com/watch?v=zHirwKGEfoE 
	
* Agrupamento de devices por configuração
  * http://developer.android.com/guide/practices/screens_support.html#ConfigurationExamples
  * http://developer.android.com/guide/practices/screens_support.html#dips-pels 
  * Classificações
    * por tamanho:
      * Phones: <600dp
      * 7" Tablets: >600dp
      * 10" Tablets: >720dp
    * por densidade:
      * LDPI: ~120dpi
      * MDPI: ~160dpi
      * HDPI: ~240dpi
      * XHDPI: ~320dpi
      * XXHDPI: ~480dpi
      * XXXHDPI: ~640dpi

* Não podemos usar pixeis para definir os layouts pois os tamanhos das views iriam variar bastante de uma densidade de tela para outra
* DIP ou DP: density independent pixels

* Android Resource Framework
  * res folder
  * o android permite fornecer versoes alternativas para os resources colocando-os em diretórios diferentes com qualificadores diferentes no nome do diretório como tamanho de tela, localizacao, o menor tamanho de tela disponivel, orientação etc.
  * mipmap: o AAPT (Android Asset Packaging Tool) pode remover assets desnecessários para resoluções que você não precisa dos diretórios drawable, se os assets estiverem no diretório mipmap eles permanecerão independete da resolução alvo do apk

* Master -> Detail flow

## Fragments

* http://developer.android.com/training/basics/fragments/fragment-ui.html
* O poder dos fragments vai além do agrupamento de elementos de UI. Eles permitem modularizar a Activity completamente incluindo os eventos de ciclo de vida que elas recebem e os estados que elas mantém.
* Não utilizar fragments é possível mas a complexidade aumenta proporcionalmente à complexidade da Activity.
* Seria possível substituir as diversas Activities de uma app por apenas uma Activity com múltiplos Fragments. Isso pode não ser adequado pois:
  * Aumento na Complexidade
  * Dificuldade em gerenciar os Intents
  * Dificuldade na leitura, manutenção e teste do código
  * Risco de alto acoplamento
  * Segurança se existir a possibilidade de misturar informações públicas e privadas do usuário
  * Um regra geral é criar uma nova Activity sempre que o contexto mudar, por exemplo ao mostrar tipos diferentes de dados ou mudando de visualização para edição de dados.
  * Possuem seu próprio ciclo de vida e UI.
  * Os eventos básicos são parecidos com a Activity
    * A UI é criada no evento onCreateView e destruída no onDestroyView
    * O FragmentManager pode trocar e mexer com os Fragments independentemente do ciclo de vida da Activity.
    * Os eventos onAttach e onDetach são as primeiras e últimas coisas a acontecer com o Fragment. onAttach é a primeira oportunidade para se obter uma referência para a Activity pai.
    * onActivityCreated é o evento que indica que o onCreate da Activity já foi concluído e indica que neste momento é seguro começar a interagir com a UI.
    * É possível criar Fragments para quebrar módulos lógicos e não apenas visuais. Como esses Fragments não possuem UI não é necessário recriá-los em mudanças de orientação.
      * No onCreate chamar setRetainInstance(true)
      * No onCreateView retornar nulo.
    * FragmentManager e Transactions
      * http://developer.android.com/reference/android/app/FragmentManager.html 
  * Static Fragments 
    * Definido explicitamente no Layout
    * <fragment android:id="fragment_id" android:name="com.example.android.AppFragment" ... />
  * Dynamic Fragments
    * Podem mudar no decorrer do uso da aplicação.
      * Declara-se uma view para marcar o lugar (um FrameLayout por exemplo)
      * E fragment transactions para carregar o Fragment dinamicamente
      * É interessante criar tags para os Framents criados dinamicamente para referenciá-los mais facilmente no FragmentManager

* Smallest Width
* ArgumentsBundle e o SavedInstanceStateBundle
* getParentActivityIntent utilizado para tratar a restauração da Activity pai.

```java
@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@Override
public Intent getParentActivityIntent() {
     return super.getParentActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
}
```

## List View, Themes and Styles

* ListView choice mode: http://developer.android.com/reference/android/widget/AbsListView.html#attr_android:choiceMode
* Style and Themes: http://developer.android.com/guide/topics/ui/themes.html 
* StateList drawable: http://developer.android.com/guide/topics/resources/drawable-resource.html#StateList 
* ListView smoothScroolToPosition
* Layout Aliasing: http://developer.android.com/training/multiscreen/screensizes.html#TaskUseAliasFilters
  * no refs.xml no diretório de resources que você quiser fazer o alias
  * <item type="layout" name="layout_file_name">@layout/layout_file_name_to_be_referenced</item>

## Design
  
* Android Design Guide: Metrics and Grids: https://developer.android.com/design/style/metrics-grids.html 
  * http://developer.android.com/design/patterns/actionbar.html
  * http://developer.android.com/guide/topics/ui/actionbar.html
  * http://developer.android.com/guide/topics/ui/actionbar.html#Logo
  * https://developer.android.com/training/basics/actionbar/styling.html
  * http://developer.android.com/reference/android/R.attr.html#displayOptions 
* Material Design color attributes
  * colorPrimaryDark
  * colorPrimary
  * colorAccent
  * colorControlNormal

* O tema é definido no arquivo styles.xml do diretorio res/xml
* sp = scaled pixels for fonts
* Tipografia: http://developer.android.com/design/style/typography.html 
* Aparência do Texto: https://plus.google.com/+AndroidDevelopers/posts/gQuBtnuk6iG 
* Cores: http://developer.android.com/guide/topics/resources/more-resources.html#Color 
* Layout Gravity: http://stackoverflow.com/questions/3482742/gravity-and-layout-gravity-on-android 

## Accessibility:

* http://developer.android.com/guide/topics/ui/accessibility/index.html
* Acessibility Checklist: http://developer.android.com/guide/topics/ui/accessibility/checklist.html
* Design Guide: http://developer.android.com/design/patterns/accessibility.html
* Testing Checklist: http://developer.android.com/tools/testing/testing_accessibility.html
* Acessibility Settings:
  * Talkback: feedback com audio para auxiliar usuários com probelmas visuais

## Custom Views

* Criar componentes visuais que não existem.
* Não reinventar a roda!
* Extender uma das classes:
  * View: mais leve com abordagem baseada no canvas
    * caixa padrão de 100px por 100px para mudar isso fazer override no método onMeasure
    * implementar o método onDraw com os desenhos necessários
    * no final do onMeasure não se esquecer de setar o tamanho final com setMeasureDimension ou a app irá crashar.
    * implementar o três contrutores principais: padrão, para um layout, e layout com tema
    * cuidado com a criação de objetos no onDraw esta operação é custosa e pode afetar a performance da UI
    * atenção para acessibilidade que pode ser complexa em views customizadas
    * Motion Events: http://developer.android.com/reference/android/view/MotionEvent.html
    * http://developer.android.com/training/custom-views/index.html 
  * SurfaceView: para componentes que exigem redraws bem rápidos ou com visuais 3D que utilizem openGL (e.g.: videos e jogos)
  
## Ferramentas Bacanas

* http://www.mobile-patterns.com/android/recently-added
* http://inspired-ui.com/
* http://unitid.nl/androidpatterns/
* http://www.lovelyui.com/
* http://speckyboy.com/2010/05/10/android-app-developers-gui-kits-icons-fonts-and-tools/
* http://android.inspired-ui.com/
* http://materialdesignicons.com/
* http://www.materialpalette.com/
* https://github.com/google/material-design-icons/releases/tag/1.0.0
* http://petrnohejl.github.io/Android-Cheatsheet-For-Graphic-Designers/
* http://www.color-hex.com/
* http://paletton.com/




