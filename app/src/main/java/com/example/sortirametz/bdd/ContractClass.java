package com.example.sortirametz.bdd;

import android.net.Uri;

public final class ContractClass {

    public static final String AUTHORITY = "com.example.sortirametz.bdd";// doit être le même nom que android:authorities de l'élément <provider>
    private static Uri.Builder builder;
    static
    {
        builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(AUTHORITY);
    }
    public static final Uri CONTENT_URI = builder.build();

    public final static class Site {
        private static Uri.Builder builder;
        static
        {
            builder = ContractClass.CONTENT_URI.buildUpon();
            builder.path("sites");
        }

        public static final Uri CONTENT_URI = builder.build();

        /**
         * colonne clef primaire : INTEGER
         * */
        public static final String id = "id";

        /**
         * colonne nom : TEXT
         * */
        public static final String site_name = "site_name";

        /**
         * colonne latitude : TEXT
         * */
        public static final String latitude = "latitude";

        /**
         * colonne longitude : TEXT
         * */
        public static final String longitude = "longitude";

        /**
         * colonne adresse : TEXT
         * */
        public static final String adresse_postale = "adresse_postale";

        /**
         * colonne categorie : INTEGER
         * */
        public static final String site_category_name = "site_category_name";

        /**
         * colonne resumé : TEXT
         * */
        public static final String resume = "resume";
    }

    public final static class Categorie {

        private static Uri.Builder builder;
        static
        {
            builder = ContractClass.CONTENT_URI.buildUpon();
            builder.path("categories");
        }

        public static final Uri CONTENT_URI = builder.build();

        /**
         * colonne clef primaire : INTEGER
         * */
        public static final String id = "id";

        /**
         * colonne nom : TEXT
         * */
        public static final String category_name = "category_name";
    }
}
