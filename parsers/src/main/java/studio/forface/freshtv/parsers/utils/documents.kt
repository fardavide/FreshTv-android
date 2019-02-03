package studio.forface.freshtv.parsers.utils

import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import java.nio.charset.Charset
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

/** An instance of [DocumentBuilder] for read xml */
private val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

/**
 * Parse a [String] xml from [DocumentBuilder]
 * @return [Document]
 */
private fun DocumentBuilder.parseContent(stringXml: String, charset: Charset = Charsets.UTF_8 ) =
        parse( stringXml.byteInputStream( charset ) )

/** @return the root [Node] of the given [documentContent] */
internal fun readDocument( documentContent: String ) =
        documentBuilder.parseContent( documentContent ).firstChild

/** @return the value of the attribute with the given [attributeName] from [Node.getAttributes] */
internal infix fun Node.attr( attributeName: String ) = attributes[attributeName]

/** @return the value of the attribute with the given [attributeName] from [NamedNodeMap] */
internal operator fun NamedNodeMap.get( attributeName: String ) =
        getNamedItem( attributeName ).nodeValue

/**
 * @return a child [Node] with the given [nodeName]
 * @throws IllegalAccessException if no [Node] if found for the given [nodeName]
 */
internal infix fun Node.child( nodeName: String ) = findChild { it.nodeName == nodeName }
        ?: throw IllegalArgumentException( "No child node found for $nodeName" )

/**
 * @return a child [Node] with the given [nodeName]
 * return null if no [Node] if found for the given [nodeName]
 */
internal infix fun Node.optChild( nodeName: String ) = findChild { it.nodeName == nodeName }

/**
 * @return a [String] [Node.getNodeValue] for the child [Node] with the given [nodeName]
 * @throws IllegalAccessException if no [Node] if found for the given [nodeName]
 */
internal infix fun Node.childValue( nodeName: String ) = optChildValue( nodeName )
        ?: throw IllegalArgumentException( "No child node found for $nodeName" )

/**
 * @return an OPTIONAL [String] [Node.getNodeValue] for the child [Node] with the given [nodeName],
 * return null if no [Node] if found for the given [nodeName]
 */
internal infix fun Node.optChildValue( nodeName: String ) =
        findChild { it.nodeName == nodeName }?.firstChild?.nodeValue

/** @return a [Node] child if [matcher] is true, return null if no [Node] if found for [matcher] */
internal inline fun Node.findChild( matcher: (Node) -> Boolean ): Node? {
    val children = childNodes
    for ( index in 0..children.length )
        children.item( index )?.let { if ( matcher( it ) ) return it }
    return null
}