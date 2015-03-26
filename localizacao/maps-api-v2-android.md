# Utilizando Google Maps Api v2 no Android

## Referências

* Getting Started: https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2
* Configurando o Google Play Services: https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2
* Documentação:
  * http://developer.android.com/google/play-services/maps.html 
  * https://developers.google.com/maps/documentation/android/ 
  * https://github.com/googlemaps 

## Guia

* Adicionar a dependência do Google Play Service ao build.gradle
  * compile 'com.google.android.gms:play-services:6.5.87'
  * Atualizar a versão sempre que houver um novo release
* Configure o Proguard conforme indicações da documentação: https://developer.android.com/google/play-services/setup.html 
* Adicionar a versão do Google Play Services ao Manifest.

```xml
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />
```

* Crie o certificado digital que irá assinar sua aplicação
  * https://developer.android.com/tools/publishing/app-signing.html 
  * Android Keystore tips
* Gere a chave do Google Maps 
  * https://console.developers.google.com 
  * Crie um projeto para sua App
  * Selecione APIs & auth, busque por Google Maps Android API v2 e mude o estado para ON
  * Selecione APIs & auth e Credentials
  * Create Key -> Android Key
  * Adicione os certificados da sua aplicação
    * Utilizar uma linha por certificado SHA1 e seguir o formato <CERTIFICADO SHA1>;<PACOTE DA APP>
    * Adicione o certificado de debug e release da sua aplicação

* Adicione a API Key à sua aplicação
  * Para facilitar crie um string resource para armazenar a chave.
  * Adicione-a como meta data no Manifest.

```xml
<resources>
    <string name= "google_maps_key" translatable="false" templateMergeStrategy= "preserve">
        AIzaSyDiDkIv_iFEdKv_KT8-hYprHuEObTxO5WM
    </string>
</resources>
```

```xml
<meta-data
    android:name= "com.google.android.maps.v2.API_KEY"
    android:value= "@string/google_maps_key" />
```

* Configurar as permissões necessárias.

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="com.google.android.providers.gsf.permission.READ_GSERVICES" />
<!--
The ACCESS_COARSE/FINE_LOCATION permissions are not required to use
     Google Maps Android API v2, but are recommended.
-->
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```
	
* Especificar a versão do OpenGL a ser utilizada.

```xml
<uses-feature
        android:glEsVersion="0x00020000"
        android:required="true"/>
```
	
* A forma mais simples de adicionar um mapa é incluir um MapFragment ou SupportMapFragment no layout.

```xml
<fragment xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools= "http://schemas.android.com/tools" android:layout_width= "match_parent"
    android:layout_height= "match_parent" android:id="@+id/map" tools:context=".MapsActivity"
    android:name= "com.google.android.gms.maps.SupportMapFragment" />
```

* E carregar o layout no onCreate da Activity como de costume.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super .onCreate(savedInstanceState);
    setContentView(R.layout. activity_maps);
    setUpMapIfNeeded() ;
}
```

* Para manipular o mapa (adicionar marcadores, janelas, mudar o tipo de mapa, posição da camera etc) é necessário obter uma instancia de GoogleMap. É possível fazer isso de duas formas:
  * Sincronamente: 
    * Lembrando que ao utilizar a chamada sincrona é necessário verificar se a instância de GoogleMap retornada é nula ou não antes de utilizá-la.
    * Essa forma não é muito segura, pois não se tem certeza de quando a instâcia de GoogleMap estará disponível.

```java  
    mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
```	

  * Assincronamente:
    * O this refere-se a uma classe que que implemente a interface OnMapReadyCallback (no caso a própria Activity).
    * Deve-se então implementar o método onMapReady(GoogleMap map) para manipular o mapa. Como no exemplo abaixo (retirado da documentação):

```java    
((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

@Override
public void onMapReady(GoogleMap map) {
    LatLng sydney = new LatLng(-33.867, 151.206);

    map.setMyLocationEnabled(true);
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

    map.addMarker(new MarkerOptions()
            .title("Sydney")
            .snippet("The most populous city in Australia.")
            .position(sydney));
}
```

* Utilizando a classe GoogleMap é possível brincar com o mapa livremente:
  * Mudar o tipo de mapa: map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
  * Utilizar Indoors Maps: map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.86997, 151.2089), 18));
  * Adicionar marcadores e janelas de informação:  
  
```java
map.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.house_flag))
            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
            .position(new LatLng(41.889, -87.622)));
```

  * Utilizar FlatMarkers:  

```java
// Flat markers will rotate when the map is rotated,
// and change perspective when the map is tilted.
map.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.direction_arrow))
        .position(mapCenter)
        .flat(true)
        .rotation(245));

CameraPosition cameraPosition = CameraPosition.builder()
        .target(mapCenter)
        .zoom(13)
        .bearing(90)
        .build();

// Animate the change in camera view over 2 seconds
map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
        2000, null);
```
    
  * Desenhar PolyLines:  

```java
// Polylines are useful for marking paths and routes on the map.        
map.addPolyline(new PolylineOptions().geodesic(true)
            .add(new LatLng(-33.866, 151.195))  // Sydney
            .add(new LatLng(-18.142, 178.431))  // Fiji
            .add(new LatLng(21.291, -157.821))  // Hawaii
            .add(new LatLng(37.423, -122.091))  // Mountain View
    );
```
