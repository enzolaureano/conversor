package org.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Conversor {


    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner conversor = new Scanner(System.in);

        menu();

        int opcion = conversor.nextInt();
        conversor.nextLine(); // Limpia el buffer de entrada

        try {


            // Valida que la opción esté en el rango correcto antes de devolverla
            if (opcion >= 0 && opcion <= 10) {

            } else {
                System.out.println("❌ Opción no válida. Por favor, elija un número del menú.");
                opcion = conversor.nextInt();
            }

        } catch (InputMismatchException e) {
            System.out.println("❌ Error: Por favor, ingrese solo un número.");
            conversor.nextLine(); // Limpia la entrada incorrecta para evitar un bucle infinito
        }



        String divisa="";

        switch (opcion) {
            case 1 -> divisa = "USD";
            case 2 -> divisa = "BRL";
            case 3 -> divisa = "EUR";
            case 4 -> divisa = "ARS";
            case 5 -> divisa = "MXN";
            case 6 -> divisa = "CLP";
            case 7 -> divisa = "BOB";
            case 8 -> divisa = "PEN";
            case 9 -> divisa = "UYU";
            case 10 -> divisa = "PYG";}

        System.out.println("\nMoneda base seleccionada: " + divisa);
        System.out.println("Ahora procederíamos a pedir la moneda de destino y el monto...");
        // Aquí llamarías a la lógica de conversión usando la variable 'monedaBase'
        System.out.println("---------------------------------------------------\n");
        // El bucle 'while' volverá a empezar, mostrando el menú de nuevo

            System.out.println("Ingresa una cantidad");

            double cantidad = conversor.nextDouble();

            String apiKey = "031e928a20845ab079c9d71d";
            String url_str = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + divisa;

            try {
                // 2. Crear el cliente y la petición HTTP
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url_str)) // ¡Usamos la URL correcta!
                        .build();

                // 3. Enviar la petición y obtener la respuesta
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    System.out.println("Error al conectar con la API: " + response.statusCode());
                    return;
                }

                String jsonResponse = response.body();
                // System.out.println(jsonResponse); // Opcional: para ver el JSON completo

                // 4. Procesar el JSON con Gson para obtener la tasa de conversión
                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
                double arsRate = conversionRates.get("ARS").getAsDouble();

                // 5. Calcular y mostrar el resultado
                double resultado = cantidad * arsRate;

                System.out.println("------------------------------------");
                System.out.printf("La tasa de cambio "+ divisa + " a ARS es: %.2f%n", arsRate);
                System.out.printf("USD %.2f equivalen a ARS %.2f%n", cantidad, resultado);
                System.out.println("------------------------------------");

            } catch (IOException | InterruptedException e) {
                System.out.println("Ocurrió un error al realizar la consulta.");
                e.printStackTrace();

            }
        }


        public static void menu() {
            System.out.println("*****************************************************");
            System.out.println("🪙 Elija la divisa a la que desea convertir:");
            System.out.println("*****************************************************");
            System.out.println(" 1. Dólar Estadounidense (USD)");
            System.out.println(" 2. Real Brasileño (BRL)");
            System.out.println(" 3. Euro (EUR)");
            System.out.println(" 4. Peso Argentino (ARS)");
            System.out.println(" 5. Peso Mexicano (MXN)");
            System.out.println(" 6. Peso Chileno (CLP)");
            System.out.println(" 7. Boliviano (BOB)");
            System.out.println(" 8. Sol Peruano (PEN)");
            System.out.println(" 9. Peso Uruguayo (UYU)");
            System.out.println("10. Guaraní Paraguayo (PYG)33");
            System.out.println("\n 0. Salir");
            System.out.println("*****************************************************");
            System.out.print("Ingrese su opción: ");
        }


    }

