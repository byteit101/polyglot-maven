package org.sonatype.maven.polyglot.kotlin.dsl

import org.codehaus.plexus.util.xml.Xpp3Dom
import org.codehaus.plexus.util.xml.Xpp3DomBuilder

@PomDsl
class PluginExecution : org.apache.maven.model.PluginExecution(), Cloneable {

    @PomDsl
    fun id(id: String): PluginExecution {
        this.id = id
        return this
    }

    @PomDsl
    fun phase(phase: String): PluginExecution {
        this.phase = phase
        return this
    }

    @PomDsl
    fun goals(vararg goals: String): PluginExecution {
        this.goals = goals.asList()
        return this
    }

    @PomDsl
    fun inherited(inherited: Boolean = true): PluginExecution {
        this.isInherited = inherited
        return this
    }

    //-- Configuration Helpers ---------------------------------------------------------------------------------------//

    /**
     * Sets the plugin configuration.
     *
     * @param source the configuration source as a [String] of XML
     */
    @PomDsl
    fun configuration(source: String): Xpp3Dom {
        this.configuration = source
        return this.configuration as Xpp3Dom
    }

    @PomDsl
    fun configuration(block: XmlNode.(XmlNode) -> Unit) {
        val configuration = XmlNode("configuration")
        block(configuration, configuration)
        this.configuration = configuration.xpp3Dom
    }

    /**
     * Sets the configuration.
     */
    override fun setConfiguration(source: Any?) {
        val name = "configuration"
        val xpp3Dom =
            when (source) {
                null -> null
                is Xpp3Dom -> source
                else -> Xpp3DomBuilder.build(source.toString().reader())
            }
        if (xpp3Dom != null && xpp3Dom.name != name) {
            val configuration = Xpp3Dom(name)
            configuration.addChild(xpp3Dom)
            super.setConfiguration(configuration)
        } else {
            super.setConfiguration(xpp3Dom)
        }
    }

    override fun clone(): org.apache.maven.model.PluginExecution {
        return super<org.apache.maven.model.PluginExecution>.clone()
    }
}
