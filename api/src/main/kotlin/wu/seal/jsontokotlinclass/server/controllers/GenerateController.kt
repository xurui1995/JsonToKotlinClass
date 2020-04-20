package wu.seal.jsontokotlinclass.server.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.library.JsonToKotlinBuilder
import wu.seal.jsontokotlinclass.server.data.entities.Hit
import wu.seal.jsontokotlinclass.server.data.repos.HitsRepo
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateRequest
import wu.seal.jsontokotlinclass.server.models.routes.generate.GenerateResponse
import wu.seal.jsontokotlinclass.server.utils.toHit

@RestController
class GenerateController {

    @Autowired
    lateinit var hitsRepo: HitsRepo

    @PostMapping("/generate")
    @ResponseBody
    fun generate(@RequestBody request: GenerateRequest): GenerateResponse {

        val builder = JsonToKotlinBuilder()

        // Integrating REST request params with builder class
        if (request.annotationLib != null) {
            builder.setAnnotationLib(TargetJsonConverter.valueOf(request.annotationLib))
        }

        if (request.classSuffix != null) {
            builder.setClassSuffix(request.classSuffix)
        }

        if (request.defaultValueStrategy != null) {
            builder.setDefaultValueStrategy(DefaultValueStrategy.valueOf(request.defaultValueStrategy))
        }

        if (request.indent != null) {
            builder.setIndent(request.indent)
        }

        if (request.commentsEnabled != null) {
            builder.enableComments(request.commentsEnabled)
        }

        if (request.createAnnotationOnlyWhenNeededEnabled != null) {
            builder.enableCreateAnnotationOnlyWhenNeeded(request.createAnnotationOnlyWhenNeededEnabled)
        }

        if (request.enableVarProperties != null) {
            builder.enableVarProperties(request.enableVarProperties)
        }

        if (request.forceInitDefaultValueWithOriginJsonValueEnabled != null) {
            builder.enableForceInitDefaultValueWithOriginJsonValue(request.forceInitDefaultValueWithOriginJsonValueEnabled)
        }

        if (request.forcePrimitiveTypeNonNullableEnabled != null) {
            builder.enableForcePrimitiveTypeNonNullable(request.forcePrimitiveTypeNonNullableEnabled)
        }

        if (request.innerClassModelEnabled != null) {
            builder.enableInnerClassModel(request.innerClassModelEnabled)
        }

        if (request.keepAnnotationOnClassAndroidXEnabled != null) {
            builder.enableKeepAnnotationOnClassAndroidX(request.keepAnnotationOnClassAndroidXEnabled)
        }

        if (request.keepAnnotationOnClassEnabled != null) {
            builder.enableKeepAnnotationOnClass(request.keepAnnotationOnClassEnabled)
        }

        if (request.mapTypeEnabled != null) {
            builder.enableMapType(request.mapTypeEnabled)
        }

        if (request.orderByAlphabeticEnabled != null) {
            builder.enableOrderByAlphabetic(request.orderByAlphabeticEnabled)
        }

        if (request.parcelableSupportEnabled != null) {
            builder.enableParcelableSupport(request.parcelableSupportEnabled)
        }

        if (request.propertyAndAnnotationInSameLineEnabled != null) {
            builder.enableAnnotationAndPropertyInSameLine(request.propertyAndAnnotationInSameLineEnabled)
        }

        if (request.packageName != null) {
            builder.setPackageName(request.packageName)
        }

        if (request.parentClassTemplate != null) {
            builder.setParentClassTemplate(request.parentClassTemplate)
        }

        if (request.propertyPrefix != null) {
            builder.setPropertyPrefix(request.propertyPrefix)
        }

        if (request.propertySuffix != null) {
            builder.setPropertySuffix(request.propertyPrefix)
        }

        if (request.propertyTypeStrategy != null) {
            builder.setPropertyTypeStrategy(PropertyTypeStrategy.valueOf(request.propertyTypeStrategy))
        }

        // Setting
        val hit = request.toHit(Hit.CLIENT_API)


        // Setting default values
        hitsRepo.save(hit)

        val json = builder.build(request.json, request.className)
        return GenerateResponse(
                GenerateResponse.Data(json),
                false,
                -1,
                "OK"
        )
    }
}