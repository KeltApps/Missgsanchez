package com.keltapps.missgsanchez.utils;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Created by sergio on 20/01/16 for KelpApps.
 */
public class FeedDatabaseTest extends AndroidTestCase {
    private FeedDatabase feedDatabase;
    private String data = "<!DOCTYPE html>\n" +
            "\n" +
            "<!-- A ThemeJug WordPress Theme  -->\n" +
            "<html lang=\"en-US\" prefix=\"og: http://ogp.me/ns#\">\n" +
            "\n" +
            "<!-- BEGIN head -->\n" +
            "<head>\n" +
            "\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
            "\t<meta name=\"viewport\" content=\"width=100%, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no\">\n" +
            "\t\n" +
            "\t<!-- Site Title -->\n" +
            "\t<title>missgsanchez.com - A fashion blogmissgsanchez.com</title>\n" +
            "\t\n" +
            "\t<!-- RSS / Pings -->\n" +
            "\t<link rel=\"alternate\" type=\"application/rss+xml\" title=\"missgsanchez.com RSS Feed\" href=\"http://missgsanchez.com/feed/rss/\" />\n" +
            "\t<link rel=\"pingback\" href=\"http://www.missgsanchez.com/xmlrpc.php\" />\n" +
            "\t\n" +
            "\t<!--[if lt IE 9]>\n" +
            "\t\t<script src=\"http://html5shim.googlecode.com/svn/trunk/html5.js\"></script>\n" +
            "\t\t<script src=\"http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js\"></script>\n" +
            "\t<![endif]-->\n" +
            "\t\n" +
            "\t<!-- BEGIN wp_head -->\n" +
            "\t<meta name=\"generator\" content=\"Galao 1.0.7 - ThemeJug.com\" />\n" +
            "\n" +
            "<!-- This site is optimized with the Yoast WordPress SEO plugin v1.7.4 - https://yoast.com/wordpress/plugins/seo/ -->\n" +
            "<meta name=\"description\" content=\"Fashion blogger based in London and Spain.\"/>\n" +
            "<link rel=\"canonical\" href=\"http://www.missgsanchez.com\" />\n" +
            "<link rel=\"next\" href=\"http://www.missgsanchez.com/page/2/\" />\n" +
            "<link rel=\"publisher\" href=\"https://plus.google.com/108501270628891691050\"/>\n" +
            "<meta property=\"og:locale\" content=\"en_US\" />\n" +
            "<meta property=\"og:type\" content=\"website\" />\n" +
            "<meta property=\"og:title\" content=\"missgsanchez.com - A fashion blog\" />\n" +
            "<meta property=\"og:description\" content=\"Missgsanchez a Fashion Blogger\" />\n" +
            "<meta property=\"og:url\" content=\"http://www.missgsanchez.com\" />\n" +
            "<meta property=\"og:site_name\" content=\"missgsanchez.com\" />\n" +
            "<meta property=\"article:publisher\" content=\"https://www.facebook.com/pages/Miss-G-Sánchez/428607860561454?fref=ts\" />\n" +
            "<meta property=\"og:image\" content=\"https://www.facebook.com/photo.php?fbid=618096798279225&amp;set=a.428608557228051.104875.428607860561454&amp;type=1&amp;theater\" />\n" +
            "<meta name=\"twitter:card\" content=\"summary\"/>\n" +
            "<meta name=\"twitter:description\" content=\"Fashion blogger based in London and Spain.\"/>\n" +
            "<meta name=\"twitter:title\" content=\"missgsanchez.com - A fashion blog\"/>\n" +
            "<meta name=\"twitter:site\" content=\"@Missgsanchez\"/>\n" +
            "<meta name=\"twitter:domain\" content=\"missgsanchez.com\"/>\n" +
            "<meta name=\"twitter:image:src\" content=\"https://www.facebook.com/photo.php?fbid=618096798279225&#038;set=a.428608557228051.104875.428607860561454&#038;type=1&#038;theater\"/>\n" +
            "<meta name=\"alexaVerifyID\" content=\"bKLqVsGim3bo-0hMg5OVQLQboxw\" />\n" +
            "<meta name=\"google-site-verification\" content=\"UA-42278805-1\" />\n" +
            "<meta name=\"p:domain_verify\" content=\"8f8dbd2be65e798773ca7f0a3dcdd2b1\" />\n" +
            "<script type=\"application/ld+json\">{ \"@context\": \"http://schema.org\", \"@type\": \"WebSite\", \"url\": \"http://www.missgsanchez.com/\", \"potentialAction\": { \"@type\": \"SearchAction\", \"target\": \"http://www.missgsanchez.com/?s={search_term}\", \"query-input\": \"required name=search_term\" } }</script>\n" +
            "<!-- / Yoast WordPress SEO plugin. -->\n" +
            "\n" +
            "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"missgsanchez.com &raquo; Feed\" href=\"http://www.missgsanchez.com/feed/\" />\n" +
            "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"missgsanchez.com &raquo; Comments Feed\" href=\"http://www.missgsanchez.com/comments/feed/\" />\n" +
            "<link rel='stylesheet' id='mobile.nav.frontend.css-css'  href='http://www.missgsanchez.com/wp-content/plugins/SD-mobile-nav/css/mobile.nav.frontend.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='blogsynthesis-scroll-to-top-style-css'  href='http://www.missgsanchez.com/wp-content/plugins/jquery-smooth-scroll/css/jss-style.min.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='arqam-style-css'  href='http://www.missgsanchez.com/wp-content/plugins/arqam/assets/style.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='wfsi-socialicons-css'  href='http://www.missgsanchez.com/wp-content/plugins/web-font-social-icons/css/icons.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<style type='text/css'>\n" +
            "\n" +
            "                    a.ptwsi-social-icon,\n" +
            "                    a.ptwsi-social-icon:visited,\n" +
            "                    .ptwsi_social-icons li a:visited,\n" +
            "                    .ptwsi_social-icons li a {\n" +
            "                            color: #000000;\n" +
            "                            background:  #ffffff;\n" +
            "                    }\n" +
            "</style>\n" +
            "<link rel='stylesheet' id='jetpack-subscriptions-css'  href='http://www.missgsanchez.com/wp-content/plugins/jetpack/modules/subscriptions/subscriptions.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='otw_sbm.css-css'  href='http://www.missgsanchez.com/wp-content/plugins/sidebar-manager-light/css/otw_sbm.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='pinterest-pinboard-widget-style-css'  href='http://www.missgsanchez.com/wp-content/plugins/pinterest-pinboard-widget/style.css?v=1&#038;ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='jquery-ui-style-plugin-css'  href='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/css/jquery-ui.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='enhance-style-css'  href='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/css/superfish.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='enhance-style-vertical-css'  href='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/css/superfish-vertical.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='menushort-style-css'  href='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/shortcode-menu.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='simplyInstagram-css'  href='http://www.missgsanchez.com/wp-content/plugins/simply-instagram/css/simply-instagram.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='prettyPhoto-css'  href='http://www.missgsanchez.com/wp-content/plugins/simply-instagram/css/simply-instagram-prettyPhoto.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='tj-sc-shortcodes-css'  href='http://www.missgsanchez.com/wp-content/plugins/themejug-shortcodes/init/css/themejug-shortcodes.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='tj-sc-fontawesome-css'  href='http://www.missgsanchez.com/wp-content/plugins/themejug-shortcodes/init/css/font-awesome.min.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='tj-sc-fontawesomeie7-css'  href='http://www.missgsanchez.com/wp-content/plugins/themejug-shortcodes/init/css/font-awesome-ie7.min.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='wplftr-css-css'  href='http://www.missgsanchez.com/wp-content/plugins/wp-lefooter/css/lefooter.css' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='wplftr-minimalist-white-css'  href='http://www.missgsanchez.com/wp-content/plugins/wp-lefooter/css/minimalist-white.css' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='wplftr-responsive-css'  href='http://www.missgsanchez.com/wp-content/plugins/wp-lefooter/css/responsive.css' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='jetpack-widgets-css'  href='http://www.missgsanchez.com/wp-content/plugins/jetpack/modules/widgets/widgets.css?ver=20121003' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='tj-style-css'  href='http://www.missgsanchez.com/wp-content/themes/galao/style.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='fonts-css'  href='http://fonts.googleapis.com/css?family=Raleway%3A400%2C700%2C500%7CKarla%3A400%2C700&#038;ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='fontAwesome-css'  href='http://www.missgsanchez.com/wp-content/themes/galao/css/font-awesome.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='response-css'  href='http://www.missgsanchez.com/wp-content/themes/galao/css/responsive.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='sharedaddy-css'  href='http://www.missgsanchez.com/wp-content/plugins/jetpack/modules/sharedaddy/sharing.css?ver=2.9.3' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='widget-grid-and-list-css'  href='http://www.missgsanchez.com/wp-content/plugins/jetpack/modules/widgets/widget-grid-and-list.css?ver=3.9.10' type='text/css' media='all' />\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/jquery.js?ver=1.11.0'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/jquery-migrate.min.js?ver=1.2.1'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/SD-mobile-nav/js/jquery.transit.min.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/SD-mobile-nav/js/jquery.touchwipe.min.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/SD-mobile-nav/js/jquery.sidr.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/SD-mobile-nav/js/mobile.nav.frontend.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/jquery-smooth-scroll/js/jss-script.min.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/js/superfish.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/js/hoverIntent.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/shortcode-menu/js/enhance.menu.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/simply-instagram/js/simply-instagram-jquery.prettyPhoto.js?ver=3.1.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/mosaic.1.0.1.min.js?ver=1'></script>\n" +
            "<link rel=\"EditURI\" type=\"application/rsd+xml\" title=\"RSD\" href=\"http://www.missgsanchez.com/xmlrpc.php?rsd\" />\n" +
            "<link rel=\"wlwmanifest\" type=\"application/wlwmanifest+xml\" href=\"http://www.missgsanchez.com/wp-includes/wlwmanifest.xml\" /> \n" +
            "<link rel='shortlink' href='http://wp.me/3asVN' />\n" +
            "\t\t<style id=\"sdrn_css\" type=\"text/css\" >\n" +
            "\t\t\t/* apply appearance settings */\n" +
            "\t\t\t#sdrn_bar {\n" +
            "\t\t\t\tbackground: #0a0a0a;\n" +
            "\t\t\t\t\t\t\t}\n" +
            "\t\t\t#sdrn_bar .menu_title, #sdrn_bar .sdrn_icon_menu {\n" +
            "\t\t\t\tcolor: #F2F2F2;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu {\n" +
            "\t\t\t\tbackground: #2E2E2E!important;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu.sdrn_levels ul li {\n" +
            "\t\t\t\tborder-bottom:1px solid #131212;\n" +
            "\t\t\t\tborder-top:1px solid #474747;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu ul li a {\n" +
            "\t\t\t\tcolor: #CFCFCF;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu ul li a:hover {\n" +
            "\t\t\t\tcolor: #606060;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu.sdrn_levels a.sdrn_parent_item {\n" +
            "\t\t\t\tborder-left:1px solid #474747;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu .sdrn_icon_par {\n" +
            "\t\t\t\tcolor: #CFCFCF;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu .sdrn_icon_par:hover {\n" +
            "\t\t\t\tcolor: #606060;\n" +
            "\t\t\t}\n" +
            "\t\t\t#sdrn_menu.sdrn_levels ul li ul {\n" +
            "\t\t\t\tborder-top:1px solid #131212;\n" +
            "\t\t\t}\n" +
            "\t\t\t\t\t\t\t#sdrn_menu, #sdrn_menu ul, #sdrn_menu li {\n" +
            "\t\t\t\t\tborder-bottom:none!important;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t\t#sdrn_menu.sdrn_levels > ul {\n" +
            "\t\t\t\t\tborder-bottom:1px solid #474747!important;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t\t.sdrn_no_border_bottom {\n" +
            "\t\t\t\t\tborder-bottom:none!important;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t\t#sdrn_menu.sdrn_levels ul li ul {\n" +
            "\t\t\t\t\tborder-top:none!important;\n" +
            "\t\t\t\t}\n" +
            "\t\t\t\n" +
            "\t\t\t#sdrn_menu.left {\n" +
            "\t\t\t\twidth:80%;\n" +
            "\t\t\t\tleft: -80%;\n" +
            "\t\t\t    right: auto;\n" +
            "\t\t\t}\n" +
            "\t\t\t\t\t\t#sdrn_menu.right {\n" +
            "\t\t\t\twidth:80%;\n" +
            "\t\t\t    right: -80%;\n" +
            "\t\t\t    left: auto;\n" +
            "\t\t\t}\n" +
            "\n" +
            "\n" +
            "\t\t\t\n" +
            "\t\t\t\n" +
            "\n" +
            "\t\t\t/* show the bar and hide othere navigation elements */\n" +
            "\t\t\t@media only screen and (max-width: 300px) {\n" +
            "\t\t\t\thtml { padding-top: 42px!important; }\n" +
            "\t\t\t\t#sdrn_bar { display: block!important; }\n" +
            "\t\t\t\t { display:none!important; }\t\t\t}\n" +
            "\t\t\t/* hide the bar & the menu */\n" +
            "\t\t\t@media only screen and (min-width: 301px) {\n" +
            "\t\t\t}\n" +
            "\n" +
            "\t\t</style>\n" +
            "\t\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\" />\n" +
            "<!-- BEGIN SimplyInstagram IE -->\n" +
            "<!-- [if IE 9]>\n" +
            "<style type=\"text/css\">\n" +
            "\n" +
            ".comment-profile{\n" +
            "\tmargin: 2px;\n" +
            "\twidth: 45px;\n" +
            "\tfloat: left;\n" +
            "}\n" +
            ".comment-profile img{\n" +
            "\tvertical-align: top;\n" +
            "}\n" +
            ".comment-holder{\n" +
            "\ttop: 0px;\n" +
            "\twidth: 200px;\n" +
            "\tfloat: left !important;\t\n" +
            "}\n" +
            "\n" +
            ".comments-holder{\n" +
            "\twidth: 210px;\n" +
            "\tfloat: left;\n" +
            "}\n" +
            "</style>\n" +
            "<![endif]-->\n" +
            "<!-- END SimplyInstagram IE -->\n" +
            "\n" +
            "<script type=\"text/javascript\">\n" +
            "\twindow._wp_rp_static_base_url = 'https://wprpp.s3.amazonaws.com/static/';\n" +
            "\twindow._wp_rp_wp_ajax_url = \"http://www.missgsanchez.com/wp-admin/admin-ajax.php\";\n" +
            "\twindow._wp_rp_plugin_version = '3.4';\n" +
            "\twindow._wp_rp_post_id = '4564';\n" +
            "\twindow._wp_rp_num_rel_posts = '5';\n" +
            "</script>\n" +
            "<link rel=\"stylesheet\" href=\"http://www.missgsanchez.com/wp-content/plugins/wordpress-23-related-posts-plugin/static/themes/vertical-m.css?version=3.4\" />\n" +
            "<style type=\"text/css\">\n" +
            ".related_post_title {\n" +
            "\tclear: both;\n" +
            "        text-align: center;\n" +
            "}\n" +
            "\n" +
            "ul.related_post {\n" +
            "\tdisplay: block;\n" +
            "\tposition: relative;\n" +
            "\tmargin:0;\n" +
            "\tpadding: 0;\n" +
            "margin-left: 30px! important;\n" +
            "margin-top: 20px! important;\n" +
            "}\n" +
            "ul.related_post li {\n" +
            "\tdisplay: inline-block;\n" +
            "\tvertical-align: top;\n" +
            "\tzoom: 1;\n" +
            "\t*display: inline;\n" +
            "\twidth: 100px;\n" +
            "\tmargin: 10px 10px 10px 0px;\n" +
            "\tpadding: 0;\n" +
            "\tbackground: none;\n" +
            "        border-radius: 50px !important;\n" +
            "margin-left: 5px! important;\n" +
            "\n" +
            "}\n" +
            "ul.related_post li a {\n" +
            "\tdisplay: block;\n" +
            "\tfont-size: 12px;\n" +
            "text-align: center!important;\n" +
            "\tline-height: 1.5em;\n" +
            "\ttext-decoration: none;\n" +
            "\tpadding-bottom: 10px;\n" +
            "\n" +
            "}\n" +
            "ul.related_post li img {\n" +
            "\tdisplay: block;\n" +
            "\twidth: 100px !important;\n" +
            "\theight: 100px !important;\n" +
            "\tfont-size: 80%;\n" +
            " border-radius: 50px !important;\n" +
            "\n" +
            "}\n" +
            "ul.related_post li small {\n" +
            "}\n" +
            "\n" +
            ".wp_rp_wrap .related_post_title {\n" +
            "margin-buttom: 20px!important;\n" +
            "}</style>\n" +
            "<link rel=\"apple-touch-icon-precomposed\" href=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/Logo_Marta_ipad.png\" />\n" +
            "<link rel=\"shortcut icon\" href=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/Favicon.png\"/>\n" +
            "<link rel=\"stylesheet\" href=\"http://www.missgsanchez.com/tj-admin-options.css?1453422697\" type=\"text/css\" media=\"screen\" />\n" +
            "\t<!-- END wp_head -->\n" +
            "<meta name=\"p:domain_verify\" content=\"8f8dbd2be65e798773ca7f0a3dcdd2b1\"/>\n" +
            "\n" +
            "<script>\n" +
            "  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){\n" +
            "  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),\n" +
            "  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)\n" +
            "  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');\n" +
            "\n" +
            "  ga('create', 'UA-42278805-1', 'missgsanchez.com');\n" +
            "  ga('send', 'pageview');\n" +
            "\n" +
            "</script>\n" +
            "<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic' rel='stylesheet' type='text/css'>\t\t\n" +
            "</head>\n" +
            "<body class=\"home blog gecko\">\n" +
            "<div class=\"header-wrap clearfix\">\n" +
            "\n" +
            "\t<header id=\"header\" class=\"clearfix\">\n" +
            "\t\n" +
            "\t\t<div class=\"logo\">\n" +
            "\t\t\t\t\t<h1><a href=\"http://www.missgsanchez.com\"><img src=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/Logo_Marta_PNG_HEADER3.png\" alt=\"missgsanchez.com\"/></a></h1>\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t<!-- BEGIN .nav -->\n" +
            "\t\t\t\t\n" +
            "\t\t\t<nav class=\"menu-home-container\"><ul id=\"menu-home\" class=\"menu\"><li id=\"menu-item-2747\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category first-menu-item menu-item-2747\"><a href=\"http://www.missgsanchez.com/outfit/\">Outfits</a></li>\n" +
            "<li id=\"menu-item-2750\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-2750\"><a href=\"http://www.missgsanchez.com/inspiration/\">Inspiration</a></li>\n" +
            "<li id=\"menu-item-2748\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-2748\"><a href=\"http://www.missgsanchez.com/trends/\">Trends</a></li>\n" +
            "<li id=\"menu-item-3178\" class=\"menu-item menu-item-type-post_type menu-item-object-page last-menu-item menu-item-3178\"><a href=\"http://www.missgsanchez.com/contact/\">Contact</a></li>\n" +
            "</ul></nav>\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t</header>\n" +
            "\n" +
            "</div>\n" +
            "<div id=\"content\" class=\"clearfix\">\n" +
            "\n" +
            "\t<div id=\"primary\" class=\"clearfix\">\n" +
            "\t\t\t\t\t\t\t\t            \n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4564\" class=\"post-4564 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit first clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Little Venice\" href=\"http://www.missgsanchez.com/2016/01/04/little-venice/\"><img width=\"680\" height=\"432\" src=\"http://www.missgsanchez.com/wp-content/uploads/2016/01/12465565_719397938160466_1824894856_o-680x432.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"12465565_719397938160466_1824894856_o\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2016/01/04/little-venice/\" rel=\"bookmark\" title=\"Permanent Link to Little Venice\">Little Venice</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Empiezo el 2016 con nuevos retos y muchas ganas de seguir con el blog para enseñaros mis looks y mis compras de cada semana. Este, en concreto, fue fotografiado hace algunos días en una de [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: January 4, 2016 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2016/01/04/little-venice/#comments\">0 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4549\" class=\"post-4549 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Vestir de traje para Nochevieja\" href=\"http://www.missgsanchez.com/2015/12/28/vestir-de-traje-para-nochevieja/\"><img width=\"680\" height=\"453\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/12/12394710_714071475359779_1630357427_o-680x453.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"12394710_714071475359779_1630357427_o\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/12/28/vestir-de-traje-para-nochevieja/\" rel=\"bookmark\" title=\"Permanent Link to Vestir de traje para Nochevieja\">Vestir de traje para Nochevieja</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Se acerca la noche de fin de año y nuestros ojos están puestos en buscar el mejor modelito para pasar la noche entre risas y amigos. Confieso que yo siempre he sido de clásicos: vestidos [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: December 28, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/12/28/vestir-de-traje-para-nochevieja/#comments\">1 Comment</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4518\" class=\"post-4518 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Copenhagen &#8211; DAY 2\" href=\"http://www.missgsanchez.com/2015/12/16/copenhagen-day-2/\"><img width=\"680\" height=\"646\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/12/missgsanchez41-680x646.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"missgsanchez4\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/12/16/copenhagen-day-2/\" rel=\"bookmark\" title=\"Permanent Link to Copenhagen &#8211; DAY 2\">Copenhagen &#8211; DAY 2</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Segundo día en Copenhague. Después de pasar la primera tarde deambulando sin rumbo por la ciudad, esa mañana decidimos realizar un tour con un guía especializado que sería el encargado de enseñarnos los principales lugares [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: December 16, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/12/16/copenhagen-day-2/#comments\">1 Comment</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4500\" class=\"post-4500 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Copenhagen &#8211; DAY 1\" href=\"http://www.missgsanchez.com/2015/12/11/copenhagen-day-1/\"><img width=\"680\" height=\"453\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/12/missgsanchez5-680x453.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"missgsanchez5\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/12/11/copenhagen-day-1/\" rel=\"bookmark\" title=\"Permanent Link to Copenhagen &#8211; DAY 1\">Copenhagen &#8211; DAY 1</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>No hay nada mejor que viajar para descrubir ciudades, respirar otros aires y volver a casa llenos de energía. Y es que la vida -Ryanair, en este caso- te brinda a veces oportunidades que no [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: December 11, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/12/11/copenhagen-day-1/#comments\">0 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4429\" class=\"post-4429 post type-post status-publish format-standard has-post-thumbnail hentry category-trends clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to 15 abrigos por menos de 100 euros\" href=\"http://www.missgsanchez.com/2015/12/04/15-abrigos-por-menos-de-100-euros/\"><img width=\"680\" height=\"461\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/12/street-style-part605_144423799478-680x461.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"street-style-part605_144423799478\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/12/04/15-abrigos-por-menos-de-100-euros/\" rel=\"bookmark\" title=\"Permanent Link to 15 abrigos por menos de 100 euros\">15 abrigos por menos de 100 euros</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Debo reconocer que esta entrada la necesito yo más que vosotras ya que ando buscando desde hace algunas semanas un abrigo, el abrigo. Admito también que tengo muchos, más de los que debería, pero es [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: December 4, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/12/04/15-abrigos-por-menos-de-100-euros/#comments\">0 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4389\" class=\"post-4389 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Blusa Vapourish Pink by LE MIEN + GIVEAWAY\" href=\"http://www.missgsanchez.com/2015/12/01/blusa-vapourish-pink-by-le-mien-giveaway/\"><img width=\"680\" height=\"450\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/11/missglemien3-680x450.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"missglemien3\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/12/01/blusa-vapourish-pink-by-le-mien-giveaway/\" rel=\"bookmark\" title=\"Permanent Link to Blusa Vapourish Pink by LE MIEN + GIVEAWAY\">Blusa Vapourish Pink by LE MIEN + GIVEAWAY</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Cuando LE MIEN se puso en contacto conmigo para una futura colaboración no dudé en decir que sí. Aunque nunca he estado en su tienda física conocía la marca desde hace un par de años [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: December 1, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/12/01/blusa-vapourish-pink-by-le-mien-giveaway/#comments\">2 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4363\" class=\"post-4363 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Iñaki&#8217;s style\" href=\"http://www.missgsanchez.com/2015/11/26/inakis-style/\"><img width=\"680\" height=\"453\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/11/iñakiretocada1-680x453.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"iñakiretocada1\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/11/26/inakis-style/\" rel=\"bookmark\" title=\"Permanent Link to Iñaki&#8217;s style\">Iñaki&#8217;s style</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Primera entrada de la semana -perdonadme, no paro de trabajar- donde esta vez el protagonista es mi chico&#8230; Hacía tiempo que le insistía en dejar que le fotografiase pero se negaba; hasta que hace menos [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: November 26, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/11/26/inakis-style/#comments\">0 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4336\" class=\"post-4336 post type-post status-publish format-standard has-post-thumbnail hentry category-array tag-array clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Fur vest at Notting Hill\" href=\"http://www.missgsanchez.com/2015/11/19/fur-vest-at-notting-hill/\"><img width=\"680\" height=\"510\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/11/IMG_4098-680x510.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"IMG_4098\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/11/19/fur-vest-at-notting-hill/\" rel=\"bookmark\" title=\"Permanent Link to Fur vest at Notting Hill\">Fur vest at Notting Hill</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Reconozco que he estado unos días ausente pero el jueves pasado&nbsp;llegué a Mallorca para celebrar mi cumpleaños y pasar unos días en familia&nbsp;y lo que menos me apetecía era tocar el ordenador; quería desconectar 100%&#8230; [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: November 19, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/11/19/fur-vest-at-notting-hill/#comments\">1 Comment</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   <p class=\"tags\">&#35;<a href=\"http://www.missgsanchez.com/tag/array/\" rel=\"tag\">Array</a></p>\t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4320\" class=\"post-4320 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Piel con piel\" href=\"http://www.missgsanchez.com/2015/11/04/piel-con-piel/\"><img width=\"680\" height=\"1026\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/11/missgsanchez3-680x1026.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"missgsanchez3\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/11/04/piel-con-piel/\" rel=\"bookmark\" title=\"Permanent Link to Piel con piel\">Piel con piel</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>Tengo una debilidad y tiene que ver con las prendas de cuero. Pantalones, tops y chaquetas de piel son imprescindibles en mi armario y cada vez crece más mi adicción (véase el post en el [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: November 4, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/11/04/piel-con-piel/#comments\">1 Comment</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\n" +
            "\t\t<!--BEGIN Article -->\n" +
            "\t\t<article id=\"post-4297\" class=\"post-4297 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit clearfix\">\n" +
            "\t\t\t\t\t\t\t\t\t    \t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"entry-content\">\n" +
            "\t\t\t\n" +
            "\t\t\t\t<div class=\"blog-hero\"><a title=\"Permanent Link to Total black in Madrid\" href=\"http://www.missgsanchez.com/2015/10/27/total-black-in-madrid/\"><img width=\"680\" height=\"453\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/10/missgsanchez113-680x453.jpg\" class=\"attachment-blog-full wp-post-image\" alt=\"missgsanchez11\" /></a></div>\n" +
            "\n" +
            "    <h3 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/10/27/total-black-in-madrid/\" rel=\"bookmark\" title=\"Permanent Link to Total black in Madrid\">Total black in Madrid</a></h3>    \n" +
            "\t\t\t\n" +
            "\t\t\t\t<p>A la hora de viajar mi prioridad es estar cómoda e inconscientemente busco las prendas más holgadas y confortables que tengo en ese momento en el armario. Prendas que se adaptan perfectamente a tu cuerpo, [&hellip;]</p>\n" +
            "\n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t<div class=\"post-meta\">\n" +
            "\t\t\t\t<p>Posted: October 27, 2015 By: <a href=\"http://www.missgsanchez.com/author/missgsanchez/\" title=\"Posts by missgsanchez\" rel=\"author\">missgsanchez</a><a href=\"\" rel=\"author\" title=\"Google Plus Profile for +\" plugin=\"Google Plus Authorship\">+</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t<p>With: <a href=\"http://www.missgsanchez.com/2015/10/27/total-black-in-madrid/#comments\">0 Comments</a></p>\n" +
            "\t\t\t\t\n" +
            "\t\t\t   \t\t\t   \n" +
            "\t\t\t</div>\n" +
            "\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t<!-- END Article -->\n" +
            "\t\t</article>\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\t\t\t\n" +
            "\t\t\t\t<div class=\"pagination-default\">\n" +
            "\t\t\t\t\t\n" +
            "\t\t\t\t\t<nav>\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t<div class=\"pagination-default-left\"><a href=\"http://www.missgsanchez.com/page/2/\" >&larr; Older posts</a></div>\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t\t<div class=\"pagination-default-right\"></div>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\t</nav>\n" +
            "\t\t\t\t\t\n" +
            "\t\t\t\t</div>\n" +
            "\t\t\t\t\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t \n" +
            "\t\t\t\t\n" +
            "\t\t\n" +
            "\t</div>\n" +
            "\t\n" +
            "\t<section id=\"sidebar\" class=\"clearfix\">\n" +
            "\n" +
            "\t<aside>\n" +
            "\t\n" +
            "\t\t<div id=\"text-12\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><img src=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/Perfil.jpg\" alt=\"some_text\" allowTransparency=\"true\" style=\"border:1px; border-radius: 8px; overflow:hidden; width:210px; height: 230px\n" +
            ";box-shadow: 0px 0px 2px #AAA !important\"; >\n" +
            "<span class=\"vcard author\"><span class=\"fn\">\n" +
            "<a href=\"http://google.com/profiles/108501270628891691050\" rel=\"author\"><?php the_author() ?>  Follow me on Google+ </a> \n" +
            "</span></span></div>\n" +
            "\t\t</div><div id=\"arqam_counter-widget-3\" class=\"widget arqam_counter-widget clearfix\"><h3 class=\"widget-title\"></h3>\t<div class=\"arqam-widget-counter arq-outer-frame arq-colored arq-col3\">\n" +
            "\t\t<ul>\t\n" +
            "\t\t\t\t<li class=\"arq-instagram\">\n" +
            "\t\t\t\t<a href=\"http://instagram.com/missgsanchez\" target=\"_blank\" >\n" +
            "\t\t\t\t\t<i class=\"arqicon-instagram-filled\"></i>\n" +
            "\t\t\t\t\t<span>4,929</span>\n" +
            "\t\t\t\t\t<small></small>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</li>\n" +
            "\t\t\t\t\t<li class=\"arq-facebook\">\n" +
            "\t\t\t\t<a href=\"http://www.facebook.com/428607860561454\" target=\"_blank\" >\n" +
            "\t\t\t\t\t<i class=\"arqicon-facebook\"></i>\n" +
            "\t\t\t\t\t<span>617</span>\n" +
            "\t\t\t\t\t<small></small>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</li>\n" +
            "\t\t\t\t\t<li class=\"arq-twitter\">\n" +
            "\t\t\t\t<a href=\"http://twitter.com/missgsanchez\" target=\"_blank\" >\n" +
            "\t\t\t\t\t<i class=\"arqicon-twitter\"></i>\n" +
            "\t\t\t\t\t<span>3,547</span>\n" +
            "\t\t\t\t\t<small></small>\n" +
            "\t\t\t\t</a>\n" +
            "\t\t\t</li>\n" +
            "\t\t\t\t\t\t\t\t\t\n" +
            "\t\t\t</ul>\n" +
            "\t\t</div>\n" +
            "\t\t<!-- Arqam Social Counter Plugin : http://codecanyon.net/user/mo3aser/portfolio?ref=mo3aser -->\n" +
            "</div><div id=\"themejug_posts_widget-2\" class=\"widget widget_themejug_posts_widget clearfix\"><h3 class=\"widget-title\">Popular Post</h3><div class=\"tj-posts-widget\"><article class=\"post-503 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit tag-assymmetrical tag-grey tag-leopard-print tag-levis tag-moccasins tag-pullover tag-vintage\"><div class=\"tj-posts-widget-featured-img\"><a title=\"Asymmetrical grey sweater\" href=\"http://www.missgsanchez.com/2013/03/04/asymmetrical-grey-pullover/\"><img width=\"150\" height=\"150\" src=\"http://www.missgsanchez.com/wp-content/uploads/DSC02274-150x150.jpg\" class=\"attachment-thumbnail wp-post-image\" alt=\"DSC02274\" /></a></div><div class=\"tj-posts-widget-post-title\"><h4 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2013/03/04/asymmetrical-grey-pullover/\" title=\"Asymmetrical grey sweater\">Asymmetrical grey sweater</a></h4></div><p class=\"tj-posts-widget-post-date\">March 4, 2013</p></article><article class=\"post-125 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit\"><div class=\"tj-posts-widget-featured-img\"><a title=\"BAROQUE RED PANTS\" href=\"http://www.missgsanchez.com/2013/02/13/baroque-red-pants/\"><img width=\"150\" height=\"150\" src=\"http://www.missgsanchez.com/wp-content/uploads/P1050469-150x150.jpg\" class=\"attachment-thumbnail wp-post-image\" alt=\"P1050469\" /></a></div><div class=\"tj-posts-widget-post-title\"><h4 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2013/02/13/baroque-red-pants/\" title=\"BAROQUE RED PANTS\">BAROQUE RED PANTS</a></h4></div><p class=\"tj-posts-widget-post-date\">February 13, 2013</p></article><article class=\"post-2996 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit\"><div class=\"tj-posts-widget-featured-img\"><a title=\"Koralline Dress + Giveaway\" href=\"http://www.missgsanchez.com/2014/03/18/koralline-dress-giveaway/\"><img width=\"150\" height=\"150\" src=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/IMG_0926-150x150.jpg\" class=\"attachment-thumbnail wp-post-image\" alt=\"IMG_0926\" /></a></div><div class=\"tj-posts-widget-post-title\"><h4 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2014/03/18/koralline-dress-giveaway/\" title=\"Koralline Dress + Giveaway\">Koralline Dress + Giveaway</a></h4></div><p class=\"tj-posts-widget-post-date\">March 18, 2014</p></article><article class=\"post-297 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit\"><div class=\"tj-posts-widget-featured-img\"><a title=\"VINTAGE LOOK\" href=\"http://www.missgsanchez.com/2013/02/20/vintage-look/\"><img width=\"150\" height=\"150\" src=\"http://www.missgsanchez.com/wp-content/uploads/IMG_1242-150x150.jpg\" class=\"attachment-thumbnail wp-post-image\" alt=\"IMG_1242\" /></a></div><div class=\"tj-posts-widget-post-title\"><h4 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2013/02/20/vintage-look/\" title=\"VINTAGE LOOK\">VINTAGE LOOK</a></h4></div><p class=\"tj-posts-widget-post-date\">February 20, 2013</p></article><article class=\"post-3643 post type-post status-publish format-standard has-post-thumbnail hentry category-outfit\"><div class=\"tj-posts-widget-featured-img\"><a title=\"Adidas Stan Smith\" href=\"http://www.missgsanchez.com/2015/03/23/adidas-stan-smith/\"><img width=\"150\" height=\"150\" src=\"http://www.missgsanchez.com/wp-content/uploads/2015/03/IMG_4749-150x150.jpg\" class=\"attachment-thumbnail wp-post-image\" alt=\"IMG_4749\" /></a></div><div class=\"tj-posts-widget-post-title\"><h4 class=\"entry-title\"><a href=\"http://www.missgsanchez.com/2015/03/23/adidas-stan-smith/\" title=\"Adidas Stan Smith\">Adidas Stan Smith</a></h4></div><p class=\"tj-posts-widget-post-date\">March 23, 2015</p></article></div></div><!-- Cached tj-posts-widget at 2016-01-21 21:15:02 --><div id=\"text-14\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><HR width=100% align=\"center\" size=\"0,5px\" color=\"#eeeeee\"/></div>\n" +
            "\t\t</div><div id=\"tj_widget_recent_tweets-2\" class=\"widget widget_tj_widget_recent_tweets clearfix\"><h3 class=\"widget-title\">Sígueme en Twitter</h3>\n" +
            "\t\t\t\t\t\t<div class=\"themejug-recent-tweets\">\n" +
            "\t\t\t\t\t\t\t<ul><li><span>¿Al final ocupa <a href=\"http://twitter.com/bonichon\" title=\"Follow bonichon\"  target=\"_blank\"  >@bonichon</a> mi lugar? <a href=\"http://twitter.com/TititiTututu\" title=\"Follow TititiTututu\"  target=\"_blank\"  >@TititiTututu</a></span><br /><a class=\"twitter_time\" target=\"_blank\" href=\"http://twitter.com/@missgsanchez/statuses/688521010052788225\">4 days ago</a></li><li><span>Aquí estoy... ¡¡¡SORPRESA, <a href=\"http://twitter.com/carlosauryn\" title=\"Follow carlosauryn\"  target=\"_blank\"  >@carlosauryn</a>!!! <a href=\"https://twitter.com/search?q=MissGS\" title=\"Search #MissGS\"  target=\"_blank\"  >#MissGS</a>ánchezinMadrid <a href=\"https://t.co/EHAu6PENUp\" title=\"https://t.co/EHAu6PENUp\"  target=\"_blank\"  >https://t.co/EHAu6PENUp</a></span><br /><a class=\"twitter_time\" target=\"_blank\" href=\"http://twitter.com/@missgsanchez/statuses/688452581987905538\">5 days ago</a></li><li><span>'A sister is both your mirror and your opposite'.\n" +
            "<a href=\"https://twitter.com/search?q=sister\" title=\"Search #sister\"  target=\"_blank\"  >#sister</a> <a href=\"https://twitter.com/search?q=familyinLondon\" title=\"Search #familyinLondon\"  target=\"_blank\"  >#familyinLondon</a> <a href=\"https://twitter.com/search?q=LosGonz\" title=\"Search #LosGonz\"  target=\"_blank\"  >#LosGonz</a>álezporelmundo <a href=\"https://t.co/NRnGRTlc4z\" title=\"https://t.co/NRnGRTlc4z\"  target=\"_blank\"  >https://t.co/NRnGRTlc4z</a></span><br /><a class=\"twitter_time\" target=\"_blank\" href=\"http://twitter.com/@missgsanchez/statuses/687034868916699137\">9 days ago</a></li>\n" +
            "\t\t\t\t\t\t\t</ul>\n" +
            "\t\t\t\t\t\t</div></div><div id=\"text-17\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><HR width=100% align=\"center\" size=\"0,5px\" color=\"#eeeeee\"/></div>\n" +
            "\t\t</div><div id=\"text-5\" class=\"widget widget_text clearfix\"><h3 class=\"widget-title\">Colaboraciones</h3>\t\t\t<div class=\"textwidget\"><p><a href=\" http://www.gloriavelazquez.com/es/12-anillos\"><img title=\"Gloria Velazquez\" src=\"http://gloriavelazquez.com/img/banners/250x250/Anillos_piedras_naturales_Anim.gif\" alt=\" Anillos Piedras Naturales\"style=\"border-radius: 5px; /></a></p></div>\n" +
            "\t\t</div><div id=\"text-13\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><HR width=100% align=\"center\" size=\"0,5px\" color=\"#eeeeee\"/></div>\n" +
            "\t\t</div><div id=\"text-8\" class=\"widget widget_text clearfix\"><h3 class=\"widget-title\">My instragram!</h3>\t\t\t<div class=\"textwidget\"><!-- www.intagme.com -->\n" +
            "<iframe src=\"http://www.intagme.com/in/?u=bWlzc2dzYW5jaGV6fHNsfDI1MHwzfDN8fHllc3w1fHVuZGVmaW5lZA==\" allowTransparency=\"true\" frameborder=\"0\" scrolling=\"no\" style=\"border:none; overflow:hidden; border-radius: 25px; width:215px; height: 215px; margin-buttom: -20px\" ></iframe></div>\n" +
            "\t\t</div><div id=\"text-9\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><a title=\"Follow missgsanchez.com on Bloglovin\" href=\"http://www.bloglovin.com/en/blog/4785127\"><img alt=\"Follow on Bloglovin\" src=\"http://www.bloglovin.com/widget/bilder/en/widget.gif?id=4785127\" border=\"0\" style=\"margin-left: 25px; margin-top: -30px\" ></a>\n" +
            "</div>\n" +
            "\t\t</div><div id=\"text-18\" class=\"widget widget_text clearfix\">\t\t\t<div class=\"textwidget\"><a data-pin-do=\"embedUser\" href=\"http://www.pinterest.com/missgsanchez/\"data-pin-scale-width=\"200\" data-pin-scale-height=\"200\" data-pin-board-width=\"400\">Visita el perfil de Miss de Pinterest.</a><!-- Please call pinit.js only once per page --><script type=\"text/javascript\" async src=\"//assets.pinterest.com/js/pinit.js\"></script></div>\n" +
            "\t\t</div>\t\n" +
            "\t</aside>\n" +
            "\t\n" +
            "</section>\n" +
            "\n" +
            "\n" +
            "</div>\n" +
            "\n" +
            "<footer id=\"footer\" class=\"clearfix\"> \n" +
            "\n" +
            "\t<div class=\"footer-inner\">\n" +
            "\n" +
            "\t\t<div class=\"footer-left\">\n" +
            "\t\t\t\t\t\t\t<p> © 2014 Missgsanchez.com | Fashion Blogger.\n" +
            "<div style=\"margin-left: 8%\"><ul class=\"ptwsi_social-icons ptwsi\"><li><a class=\"twitter standard ptwsi-social-icon\" href=\"https://twitter.com/missgsanchez\"><i class=\"ptwsi-icon-twitter\"></i></a></li><li><a class=\"facebook standard ptwsi-social-icon\" href=\"https://www.facebook.com/pages/Miss-G-S%C3%A1nchez/428607860561454?fref=ts\"><i class=\"ptwsi-icon-facebook\"></i></a></li><li><a class=\"instagram  ptwsi-social-icon\" href=\"http://instagram.com/missgsanchez\"><i class=\"ptwsi-icon-instagram\"></i></a></li><li><a class=\"rss standard ptwsi-social-icon\" href=\"http://missgsanchez.com/feed/rss/\"><i class=\"ptwsi-icon-rss\"></i></a></li></ul></div></p>\t\n" +
            "\t\t\t\t\t</div>\n" +
            "\t\t\n" +
            "\t\t<div class=\"footer-right\">\n" +
            "\t\t\t\t\t\t\t\t\n" +
            "\t\t\t\t\t<nav id=\"footer-nav\" class=\"menu-about-container\"><ul id=\"menu-about\" class=\"menu\"><li id=\"menu-item-387\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category first-menu-item menu-item-387\"><a href=\"http://www.missgsanchez.com/outfit/\">Outfit</a></li>\n" +
            "<li id=\"menu-item-388\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-388\"><a href=\"http://www.missgsanchez.com/inspiration/\">inspiration</a></li>\n" +
            "<li id=\"menu-item-389\" class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-389\"><a href=\"http://www.missgsanchez.com/trends/\">Trends</a></li>\n" +
            "<li id=\"menu-item-2944\" class=\"menu-item menu-item-type-post_type menu-item-object-page last-menu-item menu-item-2944\"><a href=\"http://www.missgsanchez.com/contact/\">Contact</a></li>\n" +
            "</ul></nav>\t\t\t\t\t\t\n" +
            "\t\t\t\t\n" +
            "\t\t</div>\n" +
            "\t\n" +
            "\t</div>\n" +
            "\t\t\t\n" +
            "</footer>\n" +
            "<div id=\"footer-sidebar\" class=\"secondary\">\n" +
            "<div id=\"footer-sidebar1\">\n" +
            "</div>\n" +
            "<div id=\"footer-sidebar2\">\n" +
            "</div>\n" +
            "<div id=\"footer-sidebar3\">\n" +
            "</div>\n" +
            "</div>\n" +
            "\n" +
            "<!-- Ran 57 queries 0.77 seconds -->\n" +
            "\t\t<a id=\"scroll-to-top\" href=\"#\" title=\"Scroll to Top\">Top</a>\n" +
            "\t\t\t<div style=\"display:none\">\n" +
            "\t</div>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.core.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.widget.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.mouse.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.resizable.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.draggable.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.button.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.position.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-includes/js/jquery/ui/jquery.ui.dialog.min.js?ver=1.10.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/wp-lefooter/js/jquery.easing.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/wp-lefooter/js/lefooter.min.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://s0.wp.com/wp-content/js/devicepx-jetpack.js?ver=201603'></script>\n" +
            "<script type='text/javascript' src='http://s.gravatar.com/js/gprofiles.js?ver=2016Janaa'></script>\n" +
            "<script type='text/javascript'>\n" +
            "/* <![CDATA[ */\n" +
            "var WPGroHo = {\"my_hash\":\"\"};\n" +
            "/* ]]> */\n" +
            "</script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/plugins/jetpack/modules/wpgroho.js?ver=3.9.10'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/superfish.js?ver=1.7.4'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/jquery.custom.js?ver=1.0'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/jquery.ui.custom.min.js?ver=1.10.3'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/jquery.fitvids.js?ver=1.0.3'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/jquery.jplayer.min.js?ver=2.5.1'></script>\n" +
            "<script type='text/javascript' src='http://www.missgsanchez.com/wp-content/themes/galao/js/jquery.flexslider-min.js?ver=2.2.0'></script>\n" +
            "\t\t<div id=\"sdrn_bar\" class=\"sdrn_bar\" data-from_width=\"300\">\n" +
            "\t\t\t<div class=\"sdrn_ic\">\n" +
            "\t\t\t\t<span class=\"sdrn_ic_1\"></span>\n" +
            "\t\t\t\t<span class=\"sdrn_ic_2\"></span>\n" +
            "\t\t\t\t<span class=\"sdrn_ic_3\"></span>\n" +
            "\t\t\t</div>\n" +
            "\t\t\t<!--<span class=\"sdrn_icon sdrn_icon_menu\" data-icon=\"m\"></span>-->\n" +
            "\t\t\t<span class=\"menu_title\">\n" +
            "\t\t\t\t\t\t\t\t\t\t\t</span>\n" +
            "\t\t</div>\n" +
            "\n" +
            "\t\t<div id=\"sdrn_menu\" class=\"sdrn_levels top \" data-custom_icon=\"\" data-custom_icon_open=\"\" data-zooming=\"no\" >\n" +
            "\t\t\t<ul id=\"sdrn_menu_ul\">\n" +
            "\t\t\t\t<li class=\"menu-item menu-item-type-taxonomy menu-item-object-category first-menu-item menu-item-2747\"><a href=\"http://www.missgsanchez.com/outfit/\">Outfits</a></li>\n" +
            "<li class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-2750\"><a href=\"http://www.missgsanchez.com/inspiration/\">Inspiration</a></li>\n" +
            "<li class=\"menu-item menu-item-type-taxonomy menu-item-object-category menu-item-2748\"><a href=\"http://www.missgsanchez.com/trends/\">Trends</a></li>\n" +
            "<li class=\"menu-item menu-item-type-post_type menu-item-object-page last-menu-item menu-item-3178\"><a href=\"http://www.missgsanchez.com/contact/\">Contact</a></li>\n" +
            "\t\t\t</ul>\n" +
            "\t\t</div>\n" +
            "\t\t<style>#leFooter-content #top-posts-2{\n" +
            "\t\t\t\twidth:287.1px;\n" +
            "\t\t\t}#leFooter-content #text-16{\n" +
            "\t\t\t\twidth:287.1px;\n" +
            "\t\t\t}#leFooter-content #text-15{\n" +
            "\t\t\t\twidth:287.1px;\n" +
            "\t\t\t}#leFooter #leFooter-inner { width:960px; }</style><script type=\"text/javascript\">\n" +
            "\tjQuery(document).ready(function($){\n" +
            "\t\t$('div#leFooter').jleFooter({easing : \"easeInOutQuad\",animSpeed : 350});\n" +
            "\n" +
            "\t});\n" +
            "</script><div id=\"leFooter\">\n" +
            "\t<div class=\"le-opener-container\">\n" +
            "\t\t<div class=\"le-opener position-left\">\n" +
            "\t\t\tToggle This\n" +
            "\t\t</div>\n" +
            "\t</div>\n" +
            "\t<div id=\"leFooter-content\">\n" +
            "\t\t<div id=\"leFooter-inner\">\n" +
            "\t\t<li id=\"top-posts-2\" class=\"widget widget_top-posts\"><h2 class=\"widgettitle\">Top Posts &amp; Pages</h2>\n" +
            "<ul><li><a href=\"http://www.missgsanchez.com/2016/01/04/little-venice/\" class=\"bump-view\" data-bump-view=\"tp\">Little Venice</a></li>\n" +
            "<li><a href=\"http://www.missgsanchez.com/2014/04/16/lemon-touch/\" class=\"bump-view\" data-bump-view=\"tp\">Lemon touch</a></li>\n" +
            "<li><a href=\"http://www.missgsanchez.com/2015/12/11/copenhagen-day-1/\" class=\"bump-view\" data-bump-view=\"tp\">Copenhagen - DAY 1</a></li>\n" +
            "<li><a href=\"http://www.missgsanchez.com/2015/10/13/bomber-jacket/\" class=\"bump-view\" data-bump-view=\"tp\">Bomber Jacket</a></li>\n" +
            "<li><a href=\"http://www.missgsanchez.com/2015/04/22/los-pantalones-de-campana-regresan/\" class=\"bump-view\" data-bump-view=\"tp\">Cómo lucir unos pantalones de campana</a></li>\n" +
            "</ul></li><li id=\"text-16\" class=\"widget widget_text\"><h2 class=\"widgettitle\">My Instragram!</h2>\n" +
            "\t\t\t<div class=\"textwidget\"><!-- www.intagme.com -->\n" +
            "<iframe src=\"http://www.intagme.com/in/?u=bWlzc2dzYW5jaGV6fHNsfDI1MHwzfDN8fHllc3w1fHVuZGVmaW5lZA==\" allowTransparency=\"true\" frameborder=\"0\" scrolling=\"no\" style=\"border:none; overflow:hidden; border-radius: 100px; width:215px; height: 215px; margin-buttom: -20px; margin-left: 30px; margin-top: 20px\" ></iframe>\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "&nbsp\n" +
            "<style>.ig-b- { display: inline-block; }\n" +
            ".ig-b- img { visibility: hidden; }\n" +
            ".ig-b-:hover {background: url(http://www.missgsanchez.com/wp-content/uploads/2014/03/Instagram_ICON_rojo.png) no-repeat 0 0;} .ig-b-:active {background: url(http://www.missgsanchez.com/wp-content/uploads/2014/03/Instagram_ICON_Negro.png) no-repeat 0 0;}\n" +
            ".ig-b-v-24 { width: 173,85px; height: 35px; background: url(http://www.missgsanchez.com/wp-content/uploads/2014/03/Instagram_ICON_Negro.png) no-repeat 0 0; margin-left: 25px; margin-top: 20px}\n" +
            "@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2 / 1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\n" +
            ".ig-b-v-24 {display: inline-block; width: 173,85px; height: 35px; background-image: url(http://www.missgsanchez.com/wp-content/uploads/2014/03/Instagram_ICON_Negro.png); background-size: 173,85px 35px; }</style>\n" +
            "<a href=\"http://instagram.com/missgsanchez?ref=badge\" class=\"ig-b- ig-b-v-24\"><img src=\"http://www.missgsanchez.com/wp-content/uploads/2014/03/Instagram_ICON_Negro.png\" alt=\"Instagram\" /></a></div>\n" +
            "\t\t</li><li id=\"text-15\" class=\"widget widget_text\"><h2 class=\"widgettitle\">About</h2>\n" +
            "\t\t\t<div class=\"textwidget\"><p><img src=\" http://www.missgsanchez.com/wp-content/uploads/2014/03/Logo_Marta_ipad.png\" alt=\"some_text\" allowTransparency=\"true\" style=\"border:1px; border-radius: none; overflow:hidden; width: 200px; height: 200px; float: left; margin-left: 50px; margin-right: auto;\"><br />\n" +
            "&nbsp</p>\n" +
            "<p style=\"text-align: center;\">Fashion blogger at missgsanchez.com</P></p>\n" +
            "<div style=\"float: left; margin-left: 70px;\n" +
            "display: block;\">\n" +
            "<ul class=\"ptwsi_social-icons ptwsi\">\n" +
            "<li><a class=\"instagram standard ptwsi-social-icon\" href=\"http://instagram.com/missgsanchez\"><i class=\"ptwsi-icon-instagram\"></i></a></li>\n" +
            "<li><a class=\"twitter standard ptwsi-social-icon\" href=\"https://twitter.com/MissGSanchez\"><i class=\"ptwsi-icon-twitter\"></i></a></li>\n" +
            "<li><a class=\"facebook standard ptwsi-social-icon\" href=\"https://www.facebook.com/pages/Miss-G-S%C3%A1nchez/428607860561454?fref=ts\"><i class=\"ptwsi-icon-facebook\"></i></a></li>\n" +
            "<li><a class=\"rss standard ptwsi-social-icon\" href=\"http://missgsanchez.com/?feed=rss2\"><i class=\"ptwsi-icon-rss\"></i></a></li>\n" +
            "</ul>\n" +
            "</div>\n" +
            "</div>\n" +
            "\t\t</li>\t\t<div style=\"clear:both;\"></div>\n" +
            "\t\t</div>\n" +
            "\t</div>\n" +
            "</div>\n" +
            "\n" +
            "\t<script src=\"http://stats.wordpress.com/e-201603.js\" type=\"text/javascript\"></script>\n" +
            "\t<script type=\"text/javascript\">\n" +
            "\tst_go({v:'ext',j:'1:2.9.3',blog:'46823503',post:'0',tz:'0'});\n" +
            "\tvar load_cmc = function(){linktracker_init(46823503,0,2);};\n" +
            "\tif ( typeof addLoadEvent != 'undefined' ) addLoadEvent(load_cmc);\n" +
            "\telse load_cmc();\n" +
            "\t</script>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

        public FeedDatabaseTest(){
        }
    @Override
    public void setUp() throws  Exception{
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        feedDatabase = FeedDatabase.getInstance(context);
    }
    @Test
    public void testformatDate_shouldReturnFormatDate(){
        // Assert.assertEquals("2016-01-04", "2016-01-04", FeedDatabase.formatDate("January 4, 2016 "));
        feedDatabase.synchronizeEntries(data);
        Assert.assertEquals("2016-01-04", 10, feedDatabase.getEntries().getCount());
    }

    @Override
    public void tearDown() throws Exception {
        feedDatabase.close();
        super.tearDown();
    }



}
