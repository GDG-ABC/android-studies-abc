# Android Keystore tips
-----------------------

Para gerar a keystore utilize a keytool como abaixo:


$ keytool -genkey -v -keystore <my-release-key.keystore>
-alias <alias_name> -keyalg RSA -keysize 2048 -validity 10000
```

Para listar os aliases em um keystore:

```
$ keytool -list -keystore <keystore name>
```

Para obter as informações de um alias específico:

```
$ keytool -list -v -keystore <keystore name> -alias <alias name>
```

## Algumas Dicas

* Com estes comandos é possível obter os fingerprints e todas as informações necessários para acesso a APIs (geramente chaves SHA1)
* A keystore de debug fica localizada em $HOME/.android/debug.keystore and the password is android
* Guarde sua keystore e senha em local seguro. Se você publicar sua aplicação e perder um dos dois é bye bye app.
* Guia para assinatura de apps: http://developer.android.com/tools/publishing/app-signing.html


