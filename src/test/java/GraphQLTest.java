import fixtures.GraphQlEndpoint;
import graphQL.AmericanExpressNodes;
import graphQL.data.DataGrQL;
import io.aexp.nodes.graphql.GraphQLResponseEntity;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;

import java.io.IOException;

public class GraphQLTest {
    @Test
    public void givenGraphQLEndpoint_whenRequestingAllBooksWithTitle_thenExpectedJsonIsReturned() throws IOException {
        GraphQLResponseEntity<DataGrQL> responseEntity = AmericanExpressNodes.callGraphQLService(GraphQlEndpoint.BASE_URI.concat(GraphQlEndpoint.READ_ENDPOINT.endPoint), "{allBooks{title}}");
        SoftAssertions.assertSoftly(softAssertions -> {
                    softAssertions.assertThat(responseEntity.getResponse().getAllUsers()).hasSize(2);
                    softAssertions.assertThat(responseEntity.getResponse().getAllUsers().get(0).getName()).isEqualTo("Title 1");
                    softAssertions.assertThat(responseEntity.getResponse().getAllUsers().get(1).getName()).isEqualTo("Title 2");
                }
        );
    }

}
