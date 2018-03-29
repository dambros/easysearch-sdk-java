package br.com.dataeasy.easysearch.sdk.client;


import br.com.dataeasy.easysearch.sdk.http.HttpMethod;
import br.com.dataeasy.easysearch.sdk.http.RequestHandler;
import br.com.dataeasy.easysearch.sdk.model.AuthenticationResponseDTO;
import br.com.dataeasy.easysearch.sdk.model.CredentialsDTO;

public class EasySearchClient {

    private static final String AUTH_PATH = "/api/auth";

    private RequestHandler requestHandler;
    private DocumentClient documentClient;

    EasySearchClient(String baseUrl, RequestHandler requestHandler) {
        this.requestHandler = requestHandler;
        this.requestHandler.init();
        this.requestHandler.setBaseUrl(baseUrl);
    }

    public String getClientId() {
        return this.requestHandler.getClientId();
    }

    public String getAccessToken() {
        return this.requestHandler.getAccessToken();
    }

    public void authenticate(CredentialsDTO credentials) {

        AuthenticationResponseDTO response = (AuthenticationResponseDTO)
                this.requestHandler.execute(
                        AUTH_PATH,
                        credentials,
                        HttpMethod.POST,
                        AuthenticationResponseDTO.class
                );

        this.requestHandler.setAuthorizationHeaders(credentials, response.getAccessToken());
    }

    public DocumentClient getDocumentClient() {
        if (this.documentClient == null) {
            this.documentClient = new DocumentClient(this.requestHandler);
        }

        return this.documentClient;
    }

}