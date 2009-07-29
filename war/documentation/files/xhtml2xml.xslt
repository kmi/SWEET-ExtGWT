<?xml version="1.0" encoding="UTF-8"?>

<!-- @author: Maria Maleshkova -->
<!-- @author: Simone Spaccarotella -->
<!-- @version: 1.0 -->
<!-- @date: 2009-05-07 -->

<xsl:stylesheet version="1.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes" />

	<xsl:template match="/">
		<hrest xsi:noNamespaceSchemaLocation="hrestSchema.xsd">
			<xsl:choose>
				<xsl:when test="//*[contains(concat(' ',normalize-space(@class),' '),' service ')]">
					<xsl:for-each select="//*[contains(concat(' ',normalize-space(@class),' '),' service ')]">
						<service>
							<xsl:if test="@id">
								<xsl:attribute name="id">
									<xsl:value-of select="@id" />
								</xsl:attribute>
							</xsl:if>
							<xsl:apply-templates mode="servicelabel" select="*" />
							<xsl:apply-templates mode="operation" select="*" />
						</service>
					</xsl:for-each>
				</xsl:when>
			</xsl:choose>
		</hrest>
	</xsl:template>

	<xsl:template match="*[contains(concat(' ',normalize-space(@class),' '),' operation ')]" mode="operation">
		<operation>
			<xsl:if test="@id">
				<xsl:attribute name="id">
					<xsl:value-of select="@id" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates mode="operationlabel" select="*" />
			<xsl:choose>
				<xsl:when test=".//*[contains(concat(' ',normalize-space(@class),' '),' method ')]">
					<xsl:apply-templates mode="operationmethod" select="*" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates mode="reverseservicemethod" select="." />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test=".//*[contains(concat(' ',normalize-space(@class),' '),' address ')]">
					<xsl:apply-templates mode="operationaddress" select="*" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:apply-templates mode="reverseserviceaddress" select="." />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:apply-templates mode="operationinput" select="*" />
			<xsl:apply-templates mode="operationoutput" select="*" />
		</operation>
	</xsl:template>

	<xsl:template match="*" mode="operation">
		<xsl:apply-templates mode="operation" select="*" />
	</xsl:template>

	<xsl:template match="*" mode="servicelabel">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' operation ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' label ')">
				<xsl:call-template name="label" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="servicelabel" select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*" mode="operationlabel">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' label ')">
				<xsl:call-template name="label" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="operationlabel" select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="label">
		<label>
			<xsl:choose>
				<xsl:when test="@title">
					<xsl:value-of select="@title" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="." />
				</xsl:otherwise>
			</xsl:choose>
		</label>
	</xsl:template>

	<xsl:template match="*" mode="operationmethod">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ') or
                            contains(concat(' ',normalize-space(@class),' '),' operation ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' method ')">
				<xsl:call-template name="method" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="operationmethod" select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="node()" mode="reverseservicemethod">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' service ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' method ')">
				<xsl:call-template name="method" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="reverseservicemethod" select="parent::*" />
				<xsl:apply-templates mode="operationmethod" select="preceding-sibling::*" />
				<xsl:apply-templates mode="operationmethod" select="following-sibling::*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="method">
		<method>
			<xsl:variable name="value">
				<xsl:choose>
					<xsl:when test="@title">
						<xsl:value-of select="@title" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="." />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:choose>
				<xsl:when test="$value='GET'    or $value='get'    or $value='Get'">
					GET
				</xsl:when>
				<xsl:when test="$value='PUT'    or $value='put'    or $value='Put'">
					PUT
				</xsl:when>
				<xsl:when test="$value='POST'   or $value='post'   or $value='Post'">
					POST
				</xsl:when>
				<xsl:when test="$value='DELETE' or $value='delete' or $value='Delete'">
					DELETE
				</xsl:when>
				<xsl:otherwise>
					<xsl:message terminate="yes">
						Unknown HTTP method:
						<xsl:value-of select="$value" />
					</xsl:message>
				</xsl:otherwise>
			</xsl:choose>
		</method>
	</xsl:template>

	<xsl:template match="*" mode="operationaddress">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ') or
                            contains(concat(' ',normalize-space(@class),' '),' output ') or
                            contains(concat(' ',normalize-space(@class),' '),' operation ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' address ')">
				<xsl:call-template name="address" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="operationaddress" select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="node()" mode="reverseserviceaddress">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' service ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' address ')">
				<xsl:call-template name="address" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="reverseserviceaddress" select="parent::*" />
				<xsl:apply-templates mode="operationaddress" select="preceding-sibling::*" />
				<xsl:apply-templates mode="operationaddress" select="following-sibling::*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="address">
		<address>
			<xsl:choose>
				<xsl:when test="@title">
					<xsl:value-of select="@title" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="." />
				</xsl:otherwise>
			</xsl:choose>
		</address>
	</xsl:template>

	<xsl:template match="*" mode="operationinput">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' output ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ')">
				<xsl:call-template name="input" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="operationinput" select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="*" mode="operationoutput">
		<xsl:choose>
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' input ')" />
			<xsl:when test="contains(concat(' ',normalize-space(@class),' '),' output ')">
				<xsl:call-template name="output" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:apply-templates mode="operationoutput"
					select="*" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="input">
		<input>
			<xsl:if test="@id">
				<xsl:attribute name="id">
					<xsl:value-of select="@id" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates mode="messagelabel" select="*" />
		</input>
	</xsl:template>

	<xsl:template name="output">
		<output>
			<xsl:if test="@id">
				<xsl:attribute name="id">
					<xsl:value-of select="@id" />
				</xsl:attribute>
			</xsl:if>
			<xsl:apply-templates mode="messagelabel" select="*" />
		</output>
	</xsl:template>

	<xsl:template match="*" mode="messagelabel">
		<xsl:for-each select="*">
			<xsl:if test="contains(concat(' ',normalize-space(@class),' '),' label ')">
				<xsl:call-template name="label" />
			</xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- todo add support for link and form operations -->

</xsl:stylesheet>
