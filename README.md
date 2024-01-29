## DEIXMAS

### Setup:

1. Na diretoria `src/`, executar os seguintes comandos:
```bash
cp backend/src/main/resources/application.properties.example backend/src/main/resources/application.properties
cp database/example.env database/.env
cp frontend/example.env frontend/.env
```
2. Preencher a propriedade `api.basketstore.key` no ficheiro `backend/src/main/resources/application.properties` com a chave da api BasketStore.

3. Ainda na diretoria `src/` correr `docker-compose up --build`.

4. No evento do reinício da aplicação, correr `docker-compose down -v` antes de a voltar a iniciar.

NOTA: Projeto inacabado.
