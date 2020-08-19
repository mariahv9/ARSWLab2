# ARSWLab2
## JAVA IMMORTALS

### Students

* Alan Yesid Marin Mendez
* Maria Fernanda Hernandez Vargas

## Part I - Before finishing class

Thread control with wait/notify. Producer/consumer

1. Check the operation of the program and run it. While this occurs, run jVisualVM and check the CPU consumption of the corresponding process. Why is this consumption? Which is the responsible class? 

La clase correspondiente de este consumo esla clase consumidor, y el consumo de CPU se debe a que esta clase deja abiertos todos los procesos incluso cuando ya se han finalizado y no hay elementos que procesar. Esto se puede ver en la herramienta JVisualVM, al cabo de 6 minutos hay 11 hilos abiertos *Imágen 1* y ejecutandose y después de 13 minutos se puede ver que hay 14 hilos en ejecución *Imágen 2*.

### Imágen 1. 6 minutos ejecución
//foto

### Imágen 2. 13 minutos ejecución
//foto

2. Make the necessary adjustments so that the solution uses the CPU more efficiently, taking into account that - for now - production is slow and consumption is fast. Verify with JVisualVM that the CPU consumption is reduced. 

Se modifica la clase consumidor y se realiza la prueba con JVisualVM.

//foto

3. Make the producer now produce very fast, and the consumer consumes slow. Taking into account that the producer knows a Stock limit (how many elements he should have, at most in the queue), make that limit be respected. Review the API of the collection used as a queue to see how to ensure that this limit is not exceeded. Verify that, by setting a small limit for the 'stock', there is no high CPU consumption or errors.
