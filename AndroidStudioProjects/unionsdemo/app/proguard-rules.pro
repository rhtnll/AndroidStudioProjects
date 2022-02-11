-keep class com.bun.miitmdid.core.** {*;}
#gdt
-keep class com.qq.e.** {
     public protected *;
 }

 #多盟聚合SDK
  -keep class com.domob.sdk.unionads.splash.UnionSplashAD{
       public <methods>;
  }

  -keep class com.domob.sdk.unionads.splash.UnionSplashAdListener{
        public <methods>;
   }

   -keep class com.domob.sdk.common.util.AdError{
        public <methods>;
   }
#多盟开屏SDK
 -keep class com.dm.sdk.ads.splash.**{
      public <methods>;
 }

  -keep class com.dm.sdk.common.util.AdError{
       public <methods>;
  }

 -keep class com.dm.sdk.ads.DMAdActivity{
        public <methods>;
   }