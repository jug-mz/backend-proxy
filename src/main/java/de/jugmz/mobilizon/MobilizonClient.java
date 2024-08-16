package de.jugmz.mobilizon;

import de.jugmz.mobilizon.model.Group;
import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;
import org.eclipse.microprofile.graphql.Query;

@GraphQLClientApi(configKey = "mobilizon-events")
public interface MobilizonClient {

    @Query(value = "group")
    Group group(@Name("preferredUsername") @NonNull String preferredUsername);

}
