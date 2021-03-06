package com.gigigo.orchextra.core.sdk.di.modules;

import com.gigigo.ggglib.network.mappers.ApiGenericResponseMapper;
import com.gigigo.orchextra.core.data.api.mappers.OcmGenericResponseMapper;
import com.gigigo.orchextra.core.data.api.mappers.article.ApiArticleElementMapper;
import com.gigigo.orchextra.core.data.api.mappers.contentdata.ApiContentDataResponseMapper;
import com.gigigo.orchextra.core.data.api.mappers.contentdata.ApiContentItemLayoutMapper;
import com.gigigo.orchextra.core.data.api.mappers.contentdata.ApiContentItemMapper;
import com.gigigo.orchextra.core.data.api.mappers.contentdata.ApiContentItemPatternMapper;
import com.gigigo.orchextra.core.data.api.mappers.elementcache.ApiElementCacheMapper;
import com.gigigo.orchextra.core.data.api.mappers.elementcache.ApiElementCachePreviewMapper;
import com.gigigo.orchextra.core.data.api.mappers.elementcache.ApiElementCacheRenderMapper;
import com.gigigo.orchextra.core.data.api.mappers.elements.ApiElementDataMapper;
import com.gigigo.orchextra.core.data.api.mappers.menus.ApiMenuContentMapper;
import com.gigigo.orchextra.core.data.api.mappers.elementcache.ApiElementCacheShareMapper;
import com.gigigo.orchextra.core.data.api.mappers.elements.ApiElementMapper;
import com.gigigo.orchextra.core.data.api.mappers.elements.ApiElementSectionViewMapper;
import com.gigigo.orchextra.core.data.api.mappers.elements.ApiElementSegmentationMapper;
import com.gigigo.orchextra.core.data.api.mappers.menus.ApiMenuContentListResponseMapper;
import orchextra.dagger.Module;
import orchextra.dagger.Provides;
import orchextra.javax.inject.Named;
import orchextra.javax.inject.Singleton;

@Module
public class ApiMapperModule {

  @Singleton @Provides ApiElementSectionViewMapper provideApiElementSectionViewMapper() {
    return new ApiElementSectionViewMapper();
  }

  @Singleton @Provides ApiElementSegmentationMapper provideAApiElementSegmentationMapper() {
    return new ApiElementSegmentationMapper();
  }

  @Singleton @Provides ApiElementMapper provideApiElementMapper(
      ApiElementSegmentationMapper apiMenuItemSegmentationMapper,
      ApiElementSectionViewMapper apiMenuItemViewMapper) {
    return new ApiElementMapper(apiMenuItemSegmentationMapper, apiMenuItemViewMapper);
  }

  @Singleton @Provides ApiMenuContentMapper provideApiMenuContentMapper(
      ApiElementMapper apiMenuItemMapper) {
    return new ApiMenuContentMapper(apiMenuItemMapper);
  }

  @Singleton @Provides ApiArticleElementMapper provideApiArticleElementMapper() {
    return new ApiArticleElementMapper();
  }

  @Singleton @Provides ApiElementCacheRenderMapper provideApiElementCacheRenderMapper(
      ApiArticleElementMapper apiArticleElementMapper) {
    return new ApiElementCacheRenderMapper(apiArticleElementMapper);
  }

  @Singleton @Provides ApiElementCachePreviewMapper provideApiElementCachePreviewMapper() {
    return new ApiElementCachePreviewMapper();
  }

  @Singleton @Provides ApiElementCacheShareMapper provideApiElementCacheShareMapper() {
    return new ApiElementCacheShareMapper();
  }

  @Singleton @Provides ApiElementCacheMapper provideApiElementCacheMapper(
      ApiElementCacheRenderMapper apiElementCacheItemRenderMapper,
      ApiElementCachePreviewMapper apiElementCachePreviewMapper, ApiElementCacheShareMapper apiElementCacheShareMapper) {
    return new ApiElementCacheMapper(apiElementCacheItemRenderMapper, apiElementCachePreviewMapper, apiElementCacheShareMapper);
  }

  @Singleton @Provides ApiElementDataMapper provideApiElementDataMapper(ApiElementCacheMapper apiElementCacheMapper) {
    return new ApiElementDataMapper(apiElementCacheMapper);
  }

  @Singleton @Provides @Named("MapperApiMenuResponse") ApiGenericResponseMapper provideApiMenuMapper(ApiMenuContentMapper apiMenuContentMapper,
      ApiElementCacheMapper apiElementCacheItemMapper) {
    return new OcmGenericResponseMapper(
        new ApiMenuContentListResponseMapper(apiMenuContentMapper, apiElementCacheItemMapper));
  }

  @Singleton @Provides ApiContentItemPatternMapper provideApiContentItemPatternMapper() {
    return new ApiContentItemPatternMapper();
  }

  @Singleton @Provides ApiContentItemLayoutMapper provideApiContentItemLayoutMapper(
      ApiContentItemPatternMapper apiContentItemPatternMapper) {
    return new ApiContentItemLayoutMapper(apiContentItemPatternMapper);
  }

  @Singleton @Provides ApiContentItemMapper provideApiContentItemMapper(
      ApiContentItemLayoutMapper apiContentItemLayoutMapper, ApiElementMapper apiElementMapper) {
    return new ApiContentItemMapper(apiContentItemLayoutMapper, apiElementMapper);
  }

  @Singleton @Provides @Named("MapperApiHomeResponse")
  ApiGenericResponseMapper provideApiHomeMapper(ApiContentItemMapper apiContentItemMapper,
      ApiElementCacheMapper apiElementCacheMapper) {
    return new OcmGenericResponseMapper(
        new ApiContentDataResponseMapper(apiContentItemMapper, apiElementCacheMapper));
  }

  @Singleton @Provides @Named("MapperApiElementCacheResponse")
  ApiGenericResponseMapper provideApiElementCacheResponseMapper(ApiElementDataMapper apiElementDataMapper) {
    return new OcmGenericResponseMapper(apiElementDataMapper);
  }

}
