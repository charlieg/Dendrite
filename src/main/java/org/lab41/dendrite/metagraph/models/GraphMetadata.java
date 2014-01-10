package org.lab41.dendrite.metagraph.models;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.modules.javahandler.JavaHandler;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerContext;
import com.tinkerpop.frames.modules.typedgraph.TypeValue;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.MapConfiguration;

import java.util.*;

@TypeValue("graph")
public interface GraphMetadata extends Metadata {

    @Property("properties")
    public Properties getProperties();

    @Property("properties")
    public void setProperties(Properties properties);

    @JavaHandler
    public Configuration getConfiguration();

    /// Return the project that owns this graph.
    @Adjacency(label = "ownsGraph", direction = Direction.IN)
    public ProjectMetadata getProject();

    /// Return all the immediate graphs that were derived from this graph.
    @Adjacency(label = "childGraph", direction = Direction.OUT)
    public Iterable<GraphMetadata> getChildGraphs();

    /// Add a new child graph.
    @Adjacency(label = "childGraph", direction = Direction.OUT)
    public void addChildGraph(GraphMetadata graph);

    /// Return the graph that this graph was derived from.
    @Adjacency(label = "childGraph", direction = Direction.IN)
    public GraphMetadata getParentGraph();

    public abstract class Impl implements JavaHandlerContext<Vertex>, GraphMetadata {

        @Override
        @JavaHandler
        public Configuration getConfiguration() {
            Properties properties = getProperties();
            if (properties == null) {
                return null;
            } else {
                return new MapConfiguration(properties);
            }
        }
    }
}