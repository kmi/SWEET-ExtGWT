<xsl:stylesheet version="1.0" 
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:xml="http://www.w3.org/XML/1998/namespace"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xml:space="default"
    xmlns:xhtml="http://www.w3.org/1999/xhtml"
    xmlns:sawsdl="http://www.w3.org/ns/sawsdl#"
    xmlns:hr="http://www.wsmo.org/ns/hrests#"
    xmlns:wsl="http://www.wsmo.org/ns/wsmo-lite#"
    >
<xsl:output indent="yes" />
    <!-- this template parses hRESTS and MicroWSMO microformats into RDF                -->
    <!-- it is intended to be used as a GRDDL transformation.                           -->
    <!-- this template does not do much input validation, so garbage-in-garbage-out     -->

    <xsl:template match="/">
        <rdf:RDF>
            <xsl:choose>
                <xsl:when test="//*[contains(concat(' ',normalize-space(@class),' '),' service ')]">
                    <xsl:for-each select="//*[contains(concat(' ',normalize-space(@class),' '),' service ')]">
                        <wsl:Service>
                            <xsl:if test="@id">
                                <xsl:attribute name="rdf:ID"><xsl:value-of select="@id"/></xsl:attribute>
                            </xsl:if>
                            <rdfs:isDefinedBy rdf:resource=""/>
                            <xsl:apply-templates mode="servicelabel" select="*" />
                            <xsl:apply-templates mode="microwsmo" select="*" />
                            <xsl:apply-templates mode="operation" select="*"/>
                        </wsl:Service>
                    </xsl:for-each>
                </xsl:when>
                <xsl:otherwise>
                    <wsl:Service>
                        <rdfs:isDefinedBy rdf:resource=""/>
                        <xsl:apply-templates mode="operation" select="*"/>
                    </wsl:Service>
                </xsl:otherwise>
            </xsl:choose>
        </rdf:RDF>
    </xsl:template>

    <xsl:template match="*[contains(concat(' ',normalize-space(@class),' '),' operation ')]" mode="operation">
        <wsl:hasOperation>
            <wsl:Operation>
                <xsl:if test="@id">
                    <xsl:attribute name="rdf:ID"><xsl:value-of select="@id"/></xsl:attribute>
                </xsl:if>
                <xsl:apply-templates mode="operationlabel" select="*"/>
                <xsl:choose>
                    <xsl:when test=".//*[contains(concat(' ',normalize-space(@class),' '),' method ')]">
                        <xsl:apply-templates mode="operationmethod" select="*"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:apply-templates mode="reverseservicemethod" select="."/>
                    </xsl:otherwise>
                </xsl:choose> 
                <xsl:choose>
                    <xsl:when test=".//*[contains(concat(' ',normalize-space(@class),' '),' address ')]">
                        <xsl:apply-templates mode="operationaddress" select="*"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:apply-templates mode="reverseserviceaddress" select="."/>
                    </xsl:otherwise>
                </xsl:choose> 
                <xsl:apply-templates mode="microwsmo" select="*" />
                <xsl:apply-templates mode="operationinput" select="*"/>
                <xsl:apply-templates mode="operationoutput" select="*"/>
            </wsl:Operation>
        </wsl:hasOperation>
    </xsl:template>
    
    <xsl:template match="*" mode="operation">
        <xsl:apply-templates mode="operation" select="*"/>
    </xsl:template>

    <xsl:template match="*" mode="servicelabel">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' operation ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' label ')">
                <xsl:call-template name="label"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="servicelabel" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="*" mode="operationlabel">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' label ')">
                <xsl:call-template name="label"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="operationlabel" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="label">
        <rdfs:label>
            <xsl:choose>
                <xsl:when test="@title"><xsl:value-of select="@title"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
            </xsl:choose>
        </rdfs:label>
    </xsl:template>

    <xsl:template match="*" mode="operationmethod">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ') or
                            contains(concat(' ',normalize-space(@class),' '),' operation ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' method ')">
                <xsl:call-template name="method"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="operationmethod" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="node()" mode="reverseservicemethod">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' service ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' method ')">
                <xsl:call-template name="method"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="reverseservicemethod" select="parent::*"/>
                <xsl:apply-templates mode="operationmethod" select="preceding-sibling::*"/>
                <xsl:apply-templates mode="operationmethod" select="following-sibling::*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="method">
        <hr:hasMethod> 
            <xsl:variable name="value">
                <xsl:choose>
                    <xsl:when test="@title"><xsl:value-of select="@title"/></xsl:when>
                    <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
                </xsl:choose>
            </xsl:variable>
            <xsl:choose>
                <xsl:when test="$value='GET'    or $value='get'    or $value='Get'"   >GET</xsl:when>
                <xsl:when test="$value='PUT'    or $value='put'    or $value='Put'"   >PUT</xsl:when>
                <xsl:when test="$value='POST'   or $value='post'   or $value='Post'"  >POST</xsl:when>
                <xsl:when test="$value='DELETE' or $value='delete' or $value='Delete'">DELETE</xsl:when>
                <xsl:otherwise>
                    <xsl:message terminate="yes">Unknown HTTP method: <xsl:value-of select="$value"/></xsl:message>
                </xsl:otherwise>
            </xsl:choose>
        </hr:hasMethod>
    </xsl:template>


    <xsl:template match="*" mode="operationaddress">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ') or
                            contains(concat(' ',normalize-space(@class),' '),' operation ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' address ')">
                <xsl:call-template name="address"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="operationaddress" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="node()" mode="reverseserviceaddress">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' service ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' address ')">
                <xsl:call-template name="address"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="reverseserviceaddress" select="parent::*"/>
                <xsl:apply-templates mode="operationaddress" select="preceding-sibling::*"/>
                <xsl:apply-templates mode="operationaddress" select="following-sibling::*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="address">
        <hr:hasAddress rdf:datatype="http://www.wsmo.org/ns/hrests#URITemplate">
            <xsl:choose>
                <xsl:when test="@title"><xsl:value-of select="@title"/></xsl:when>
                <xsl:otherwise><xsl:value-of select="."/></xsl:otherwise>
            </xsl:choose>
        </hr:hasAddress>
    </xsl:template>

    <xsl:template match="*" mode="operationinput">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' output ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ')">
                <xsl:call-template name="input"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="operationinput" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="*" mode="operationoutput">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' output ')">
                <xsl:call-template name="output"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="operationoutput" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="input">
        <wsl:hasInputMessage>
            <wsl:Message>
                <xsl:if test="@id">
                    <xsl:attribute name="rdf:ID"><xsl:value-of select="@id"/></xsl:attribute>
                </xsl:if>
                <xsl:apply-templates mode="messagelabel" select="*"/>
                <xsl:apply-templates mode="microwsmo" select="*" />
            </wsl:Message>
        </wsl:hasInputMessage>
    </xsl:template>

    <xsl:template name="output">
        <wsl:hasOutputMessage>
            <wsl:Message>
                <xsl:if test="@id">
                    <xsl:attribute name="rdf:ID"><xsl:value-of select="@id"/></xsl:attribute>
                </xsl:if>
                <xsl:apply-templates mode="messagelabel" select="*"/>
                <xsl:apply-templates mode="microwsmo" select="*" />
            </wsl:Message>
        </wsl:hasOutputMessage>
    </xsl:template>

    <xsl:template match="*" mode="messagelabel">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' label ')">
                <xsl:call-template name="label"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="messagelabel" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="*" mode="microwsmo">
        <xsl:choose>
            <xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ') or
                            contains(concat(' ',normalize-space(@class),' '),' operation ')" />
            <xsl:when test="contains(concat(' ',normalize-space(@rel),' '),' model ')">
                <xsl:call-template name="model"/>
            </xsl:when>
            <xsl:when test="contains(concat(' ',normalize-space(@rel),' '),' lifting ')">
                <xsl:call-template name="lifting"/>
            </xsl:when>
            <xsl:when test="contains(concat(' ',normalize-space(@rel),' '),' lowering ')">
                <xsl:call-template name="lowering"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates mode="microwsmo" select="*"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
        
    <xsl:template name="model">
        <sawsdl:modelReference rdf:resource="{@href}"/>
    </xsl:template>

    <xsl:template name="lifting">
        <sawsdl:liftingSchemaMapping rdf:resource="{@href}"/>
    </xsl:template>

    <xsl:template name="lowering">
        <sawsdl:loweringSchemaMapping rdf:resource="{@href}"/>
    </xsl:template>


    <!-- todo add support for link and form operations -->


</xsl:stylesheet>
