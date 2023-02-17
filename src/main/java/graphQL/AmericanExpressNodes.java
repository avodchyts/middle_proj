package graphQL;

import graphQL.data.DataGrQL;
import io.aexp.nodes.graphql.GraphQLRequestEntity;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import io.aexp.nodes.graphql.GraphQLTemplate;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class AmericanExpressNodes {
    public static GraphQLResponseEntity<DataGrQL> callGraphQLService(String url, String query) throws IOException {
        GraphQLTemplate graphQLTemplate = new GraphQLTemplate();

        GraphQLRequestEntity requestEntity = GraphQLRequestEntity.Builder()
                .url(StringUtils.join(url, "?query=", query))
                .request(DataGrQL.class)
                .build();

        return graphQLTemplate.query(requestEntity, DataGrQL.class);
    }
}
