# Utilizando a Recycler View e CardView

## O que é RecyclerView

RecyclerView é a evolução da tradicional ListView do Android.

Ela foi criada para ser mais avançada, flexível e performática que a ListView. A principal diferença é a obrigatoriedade de implementação do padrão ViewHolder, que ajuda nas questões de performance mas não era obrigatório na ListView.

Um ViewHolder é utilizado para armazenar referências para os componentes de UI para cada item do layout da lista. Isto torna o processo de bind dos dados na view mais rápido já que não temos que ficar fazendo o lookup dos componentes na hierarquia do layout utilizando o findViewById.

Para implementar é importante entender três partes:A

* RecyclerView.Adapter: define como os dados serão ligados aos componentes de UI dos itens da lista.
* RecyclerView.ViewHolder: guarda as referências para os componentes de UI dos itens da lista.
* RecyclerView.LayoutManager: responsável pela organização dos itens no layout, medição e posicionamento dos itens e a reciclagem de itens que não estão sendo mais utilizados. As implementações disponíveis são a LinearLayoutManager e StaggeredGridLayoutManager.

### Passos para a Implementação

* Incluir a biblioteca de suporte no build.gradle


```
...

dependencies {
    ...
    compile 'com.android.support:recyclerview-v7:22.0.0'
}
```


* Adicionar o RecyclerView ao layout da tela.


```xml

...

    <android.support.v7.widget.RecyclerView
        android:id="@+id/myRecyclerView"
        android:scrollbars="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

...

```


* Criar um layout para o seu list item


```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/myListItem"
    android:layout_gravity="center"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp">

    <TextView
        android:id="@+id/myTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textAppearance="@android:style/TextAppearance.Medium"/>

</FrameLayout>
```


* Implementar o RecyclerView.Adapter e RecyclerView.ViewHolder


```java
package gdgabc.recyclerviewdemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public ViewHolder(View rootView) {
            super(rootView);
            this.mTextView = (TextView) rootView.findViewById(R.id.myTextView);
        }
    }

    public MyRecyclerViewAdapter(String[] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataSet[position]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
```


* Finalmente configurar o LayoutManager e Adapter do RecyclerView no Fragment ou Activity onde ele está sendo usado.


```java
        // um método como esse pode ser criado e chamado no onCreate ou onCreateView
        private void setUpRecyclerView(View rootView, String[] myDataSet) {
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.myRecyclerView);
            mRecyclerView.setHasFixedSize(true);

            mRCLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mRCLayoutManager);

            mRVAdapter = new MyRecyclerViewAdapter(myDataSet);
            mRecyclerView.setAdapter(mRVAdapter);
        }
```

## O que é o CardView

O CardView é um novo componente de UI introduzido no Lollipop que permite mostrar informações dentro de um card e customizar sua aparência. Geralmente sua utilização está associada a uma lista.

### Passos para Implementação


* Incluir a biblioteca de suporte no build.gradle


```
...

dependencies {
    ...
        compile 'com.android.support:cardview-v7:22.0.0'
}
```


* Criar o layout do item utilizando o CardView


```xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:card_view="http://schemas.android.com/apk/res-auto"
    android:id="@+id/myCardView"
    android:layout_gravity="center"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="4dp"
    card_view:cardCornerRadius="4dp"
    card_view:contentPadding="8dp">

    <TextView
        android:id="@+id/myTextView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:textAppearance="@android:style/TextAppearance.Medium"/>

</android.support.v7.widget.CardView>
```

## Referências

* Documentação do RecyclerView: https://developer.android.com/reference/android/support/v7/widget/RecyclerView.html 
* Documentação do CardView: https://developer.android.com/reference/android/support/v7/widget/CardView.html 
