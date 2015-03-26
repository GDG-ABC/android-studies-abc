# Obtendo a Localização do Usuário com LocationServices

# Referências

* http://developer.android.com/guide/topics/location/index.html
* http://developer.android.com/guide/topics/location/strategies.html 
* Google Location Services: http://developer.android.com/google/play-services/location.html 
* Google Play Services SetUp: http://developer.android.com/google/play-services/setup.html 

# Guia

* É possível utiliza diversas APIs para lidar com localização no Android. As duas principais são:
  * APIs disponíveis no pacote android.location
    * São APIs de baixo nível do Android
    * É responsabilidade do desenvolvedor lidar com questões como economia de energia, reconhecer informações contextuais, LocationProviders etc.
  * Google Location APIs
    * Possui algumas facilidades e abstrai algumas coisas para o desenvolvedor.
    * Utiliza o FusedLocationProvider para obter rapidamente informações de localização do usuário cruzando diversas fontes (como o gps do celular e do carro por exemplo)
    * Geofencing
    * Reconhecimento de atividades do usuário.
    * http://developer.android.com/training/location/index.html 
    * http://developer.android.com/reference/com/google/android/gms/location/package-summary.html 


## Utilizando o Google Location APIs
	
* Configurar a dependencia no Gradle: compile 'com.google.android.gms:play-services-location:7.0.0'
* Existem 4 atividades básicas:
  * Obter a última localização conhecida
  * Receber updates de localização
  * Mostrar o endereço de uma localização (reverse geocoding)
  * Criar e monitorar Geofences
* Obtendo a última localização conhecida:
  * Configurar as permissões no Manifesto para acesso a localização de acordo com a necessidade: 

```xml
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> 
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```
	
  * Conectar ao Google Play Services:
    * Criar uma instancia do GoogleApiClient no onCreate de sua Activity e utilizar o builder para adicionar o LocationServices.
    * É possível definir um método para criação e chamá-lo no onCreate como abaixo.

```java
protected synchronized void buildGoogleApiClient() {
    mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
}
```
	
    * Implementar as interfaces de callback ConnectionCallbacks e OnConnectionFailedListener (no caso pode ser sua própria Activity)
    * Uma vez que a app estiver conectada ao google play services será chamado o método: onConnected(Bundle connectionHint) onde é possível utilizar a Fused Locatio Api para obter a localização do usuário como no método abaixo.

```java
@Override
public void onConnected(Bundle connectionHint) {
    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
            mGoogleApiClient);
    if (mLastLocation != null) {
        mLastLocation .getLatitude();
        mLastLocation .getLongitude();
    }
}
```
