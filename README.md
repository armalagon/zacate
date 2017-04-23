# zacate


## Requisitos de sistema

* Java 8+: El proyecto usa algunas de las nuevas caracteristicas de Java 8.
* Maven 3.x.
* IDE: Cualquier IDE que tenga soporte para proyectos maven o un editor de texto si no se desea usar un IDE.

## Convertir un numero a letra

La clase `SpanishNumber2Letter` permite convertir numeros (enteros y decimales) a letras. A traves de una clase *builder* se puede configurar
la moneda, formato largo para las decenas y para el caso de los numeros enteros, una bandera para indicar si se debe mostrar la parte decimal
con el valor 0/100.

A continuacion un ejemplo de como usar la clase:

        Number2Letter converter = new SpanishNumber2LetterBuilder(106).build();
        converter.toLetter(); // ciento seis

        Number2Letter converter = new SpanishNumber2LetterBuilder(16).longFormat(true).build();
        converter.toLetter(); // diez y seis

        Number2Letter converter = new SpanishNumber2LetterBuilder(17).build();
        converter.toLetter(); // diecisiete

        Number2Letter converter = new SpanishNumber2LetterBuilder(new BigDecimal("1250.85"))
                .currency("cordoba(s)")
                .build();
        converter.toLetter(); // un mil doscientos cincuenta cordoba(s) con 85/100

        Number2Letter converter = new SpanishNumber2LetterBuilder(999)
                .longFormat(true)
                .alwaysShowFractionPart(true)
                .currency("cordoba(s)")
                .build();
        converter.toLetter(); // novecientos noventa y nueve cordoba(s) con 0/100

Para buscar mas ejemplos, revisar la clase `SpanishNumber2LetterTest`.

## Validar una cedula nicarag&#252;ense

La clase `Cedula` permite determinar si una cedula es valida y si lo es, obtener las secciones que la componen: codigo del municipio, fecha de
nacimiento, consecutivo, digito verificador y la cedula formateada con guiones para una mejor visualizacion. En caso de ser una cedula incorrecta,
las secciones retornan `null`.

A continuacion un ejemplo de como usar la clase:

        Cedula cedula = new Cedula("2811311830009v");
        cedula.isValid(); // true
        cedula.getRaw(); // La cadena original pasada al constructor de la clase
        cedula.getDistrict(); // 281
        cedula.getBirthday(); // 131183
        cedula.getConsecutive(); // 0009
        cedula.getDigit(); // v
        cedula.getFormatted(); // 281-131183-0009v

        // La siguiente cedula es incorrecta porque la fecha no es valida
        cedula = new Cedula("2813201830008V");
        cedula.isValid(); // false
        cedula.getRaw(); // La cadena original
        cedula.getDistrict(); // null
        cedula.getBirthday(); // null
        cedula.getConsecutive(); // null
        cedula.getDigit(); // null
        cedula.getFormatted(); // null

Mas ejemplos de como usar la clase estan disponibles en el test `CedulaTest`.
