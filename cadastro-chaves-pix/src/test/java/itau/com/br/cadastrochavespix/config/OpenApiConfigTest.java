package itau.com.br.cadastrochavespix.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OpenApiConfigTest {

    @Test
    public void testCustomOpenAPI_Configuration() {
        // Cria uma instância da classe OpenApiConfig
        OpenApiConfig openApiConfig = new OpenApiConfig();
        // Obtém o OpenAPI personalizado
        OpenAPI customOpenAPI = openApiConfig.customOpenAPI();
        // Verifica se o OpenAPI foi configurado corretamente
        assertEquals("API Rest Gerenciadora de Chave PIX", customOpenAPI.getInfo().getTitle());
        assertEquals("v1", customOpenAPI.getInfo().getVersion());
        assertEquals("Case Itau", customOpenAPI.getInfo().getDescription());
        assertEquals("Apache 2.0", customOpenAPI.getInfo().getLicense().getName());
        assertEquals("https://www.apache.org/licenses/LICENSE-2.0", customOpenAPI.getInfo().getLicense().getUrl());
    }
}
