# MediaPollingClientComponent

`MediaPollingClientComponent` es un componente Java Swing (JavaBean) que actúa como intermediario entre tu interfaz gráfica y la API.

Permite:

- Hacer **polling** al servidor cada X segundos para detectar nuevos archivos `Media`.
- Lanzar un **evento personalizado** cuando se detectan nuevos elementos.
- Contiene métodos **wrapper** sobre `ApiClient` (`login`, `getAllMedia`, `download`, `uploadFileMulti`, etc.), para que tu GUI no tenga que usar `ApiClient` directamente.

## 1. Descarga del componente

Para usar el componente en un proyecto puedes usar directamente el JAR publicado en GitHub.

1. Entra en este repositorio en GitHub.
2. Ve a la pestaña **Releases**.
3. Descarga el archivo JAR, por ejemplo:  
   `MediaPollingClientComponent-1.0-SNAPSHOT.jar`  

Guárdalo en alguna carpeta de tu elección.

---

## 2. Añadir el JAR a tu proyecto (NetBeans)

1. En NetBeans, haz clic derecho sobre tu proyecto → **Properties**.
2. Ve a la sección **Libraries**.
3. Pulsa **Add JAR/Folder**.
4. Selecciona el JAR que has descargado desde GitHub.
5. Acepta los cambios y compila el proyecto.

A partir de aquí ya puedes importar y usar las clases de mi componente.

## 3. Importar mi componente en tu código

```java
import mosqueira.mediapollingcomponent.component.MediaPollingClientComponent;
import mosqueira.mediapollingcomponent.MediaPollingClientListener;
import mosqueira.mediapollingcomponent.MediaPollingClientEvent;
import mosqueira.mediapollingcomponent.model.Media;
```
## 4. Uso básico del componente

### 4.1. Crear y configurar el componente

```java
MediaPollingClientComponent mediaPollingClientComponent = new MediaPollingClientComponent();

// Configuración básica del componente
mediaPollingComponent.setApiUrl("url....");
mediaPollingComponent.setPollingInterval(5);   // cada 5 segundos
mediaPollingComponent.setToken(tokenValido);   // tu token JWT
mediaPollingComponent.setRunning(true);        // empieza el polling
```

## 5. Eventos: MediaPollingClientListener

El componente utiliza la interfaz MediaPollingClientListener para notificar cuando se detectan nuevos archivos en la red Media.
```java
mediaPollingClientComponent.addMediaPollingClientListener(new MediaPollingListener() {
    @Override
    public void onNewMediaDetected(MediaPollingClientEvent event) {
        System.out.println("Nuevos medios detectados en: " + event.getDateEvent());
        for (Media m : event.getNewMedia()) {
            System.out.println(" - " + m.getFileName());
            // Aquí puedes actualizar una tabla, lista, o mostrar una notificación en la UI
        }
    }
});
```
