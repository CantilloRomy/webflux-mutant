# webflux-mutant
API para conocer a partir de un vector de String (ADN), si una persona es mutante o no.

Para realizar peticiones satisfactorias al API se debe tener en cuenta las siguientes especificaciones

- Los caracteres permitidos para realizar la validacion son A, G, C, T
- Una persona es mutante cuando en la secuencia de caracteres, se encuentran seguidas 4 o mas caracteres iguales ej AAAA
- La secuencia de caracteres de cada string debe tener una longitud igual o mayor a 4
- url /api/v1/dna

La API tiene 2 endpoint

 - /mutant 
 
    - el cual permite verificar si una persona es mutante
    - json ejemplo para peticion
    {
      "dna" : ["AFTCG", "AFTCG", "AFTCG", "AFTCG"]
    }
    
 - /stats
    - Permite tener un resumen de los validaciones que se han realizado
    - Al consumir el servicio se obtendra un json
    {
      "count-mutant-dna" : 40,
      "count-human-dna" : 100,
      "ratio" : 04
    }
