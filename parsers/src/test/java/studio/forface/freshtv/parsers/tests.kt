package studio.forface.freshtv.parsers

val mockEpgContent = """
<?xml version="1.0" encoding="UTF-8"?>
<tv generator-info-name="WebGrab+Plus/w MDB &amp; REX Postprocess -- version V2.1.5 -- Jan van Straaten" generator-info-url="http://www.webgrabplus.com">
  <channel id="IT PRIMAFILA 19">
    <display-name lang="it">IT PRIMAFILA 19</display-name>
    <url>http://www.guidatv.sky.it</url>
  </channel>
  <channel id="PRIMAFILA 19HD">
    <display-name lang="it">PRIMAFILA 19HD</display-name>
    <url>http://www.guidatv.sky.it</url>
  </channel>
  <channel id="PrimaFila 19">
    <display-name lang="it">PrimaFila 19</display-name>
    <url>http://www.guidatv.sky.it</url>
  </channel>
  <channel id="Rai Italia">
    <display-name lang="it">Rai Italia</display-name>
    <url>http://www.raitalia.it</url>
  </channel>
  <channel id="RAI ITALIA">
    <display-name lang="it">RAI ITALIA</display-name>
    <url>http://www.raitalia.it</url>
  </channel>
  <programme start="20190202060000 +0100" stop="20190202065500 +0100" channel="Rai Uno">
    <title lang="it">Il caffe' di Raiuno</title>
    <desc lang="it">Cinzia Tani e Guido Barlozzetti, con la partecipazione di Gemma Favia, mettono al centro della narrazione la curiosita' per i fenomeni sociali e culturali meno indagati dal racconto televisivo.(n)</desc>
    <category lang="it">Magazine Cultura</category>
    <rating>
      <value>per tutti</value>
    </rating>
  </programme>
  <programme start="20190202065500 +0100" stop="20190202070000 +0100" channel="Rai Uno">
    <title lang="it">Gli imperdibili</title>
    <desc lang="it">Backstage, anteprime, eventi e curiosita' dentro lo schermo nel programma che offre informazione e approfondimento sulla programmazione dei canali che compongono il bouquet Rai.(n)</desc>
    <category lang="it">Mondi e culture</category>
    <rating>
      <value>per tutti</value>
    </rating>
  </programme>
  <programme start="20190202070000 +0100" stop="20190202070500 +0100" channel="Rai Uno">
    <title lang="it">Tg1</title>
    <desc lang="it">Le notizie del giorno e gli approfondimenti su temi di stretta attualita' nel telegiornale di RaiUno.(n)</desc>
    <category lang="it">Notiziario</category>
    <rating>
      <value>per tutti</value>
    </rating>
  </programme>
</tv>
""".trimIndent()

val mockPlaylistContent = """
#EXTM3U

#EXTINF:-1 tvg-id="" group-title="RAI HD" tvg-logo="http://www.rai.it/dl/img/2016/09/1473429803381logo-rai1-2016.png",Rai 1 HD
http://bit.ly/rai1qdr

#EXTINF:-1 tvg-id="" group-title="RAI HD" tvg-logo="http://www.rai.it/dl/img/2016/09/1473149101555logo-rai2-2016.png",Rai 2 HD
http://bit.ly/rai2qdr

#EXTINF:-1 tvg-id="" group-title="RAI HD" tvg-logo="http://www.rai.it/dl/img/2016/09/1473429907779logo-rai3-2016.png",Rai 3 HD
http://bit.ly/rai3qdr

#EXTINF:-1 tvg-id="" group-title="MEDIASET" tvg-logo="https://upload.wikimedia.org/wikipedia/it/thumb/2/28/Rete_4.svg/100px-Rete_4.svg.png",Rete 4
https://origin-free-live1.mediaset.net/content/hls_clr_xo/live/channel(ch03)/index.m3u8

#EXTINF:-1 tvg-id="" group-title="MEDIASET" tvg-logo="https://i.imgur.com/dd2qJlg.png",Canale 5
https://origin-free-live1.mediaset.net/content/hls_clr_xo/live/channel(ch01)/index.m3u8

#EXTINF:-1 tvg-id="" group-title="MEDIASET" tvg-logo="https://upload.wikimedia.org/wikipedia/it/thumb/3/30/Logo_Italia_1.svg/200px-Logo_Italia_1.svg.png",Italia 1
https://origin-free-live1.mediaset.net/content/hls_clr_xo/live/channel(ch02)/index.m3u8

#EXTINF:-1 group-title="CREDITS" tvg-logo="http://i.imgur.com/eGcoFU4.png",[COLOR blue][B]Canali pay (solo per guida TV)[/B][/COLOR]
http://bit.ly/qdrforumads

#EXTINF:-1,>>>>>Sky Cinema<<<<<
http://ksportiptv.com:1557/live/metti/metti/4219.ts
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)", tvg-logo="https://upload.wikimedia.org/wikipedia/it/thumb/b/b4/Sky3D-IT-channel-plinth-RGB-3.png/260px-Sky3D-IT-channel-plinth-RGB-3.png", Sky 3D
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)", tvg-logo="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Sky_cinema_hd_2016.png/280px-Sky_cinema_hd_2016.png", Sky Cinema 1
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)", tvg-logo="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3d/Sky_cinema_hd_2016.png/280px-Sky_cinema_hd_2016.png", Sky Cinema 1 +24 HD
http://bit.ly/qdrforum

#EXTINF:-1,>>>>>SCIENZA<<<<<
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)", Prima Fila 19 LIVE
http://bit.ly/qdrforum
#EXTINF:-1,  ======== MEDIASET PREMIUM SPORT ========
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)",  Premium Calcio 5
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)",  Premium Calcio 6
http://bit.ly/qdrforum
#EXTINF:-1 tvg-id="" group-title="PAY TV (Solo per guida)",  Premium Calcio 7
http://bit.ly/qdrforum

#EXTINF:-1, === LPC/Lega Pro Ch. ===
http://bit.ly/qdrforum

#EXTINF:-1,  Lega Pro Catania
http://bit.ly/qdrforum

#EXTINF:-1 group-title="CREDITS" tvg-logo="http://i.imgur.com/eGcoFU4.png",[COLOR blue][B]ALTRI CANALI[/B][/COLOR]
http://bit.ly/qdrforum

#EXTINF:-1 group-title="NO CATEGORY" tvg-logo="1000.png",[COLOR green][B]         CANALI REGIONALI[/B][/COLOR]
plugin://plugin.video.youtube/

#EXTINF:-1 tvg-id="" group-title="ABRUZZO" tvg-logo="093.png",Rete 8 News 24
rtsp://live.cdn2.streamago.tv/streamago/39648/33591/?idcanale=33591

#EXTINF:-1 tvg-id="" group-title="EMILIA ROMAGNA" tvg-logo="145.png",Icaro Tv
http://open.http.mp.streamamg.com/p/3000380/sp/300038000/playManifest/entryId/0_xgibi59e/format/applehttp/protocol/http/uiConfId/30011075/a.m3u8

#EXTINF:-1 tvg-id="" group-title="FRIULI VENEZIA GIULIA" tvg-logo="146.png",Company TV
rtmp://wma10.fluidstream.net/CompanyTV/_definst_/mp4:livestream_720p
#EXTINF:-1 tvg-id="" group-title="FRIULI VENEZIA GIULIA-VENETO" tvg-logo="148.png",Antenna 3
rtsp://live.cdn2.streamago.tv/streamago/34674/28723/?idcanale=28723
#EXTINF:-1 tvg-id="" group-title="FRIULI VENEZIA GIULIA" tvg-logo="149.png",Telepordenone
rtmp://213.187.12.18/telepn/telepn
#EXTINF:-1 tvg-id="" group-title="FRIULI VENEZIA GIULIA" tvg-logo="150.png",Sportelevision
http://37.59.17.33:1935/sportelevision_live/smil:channel.smil/playlist.m3u8

#EXTINF:-1 tvg-id="" group-title="LOMBARDIA" tvg-logo="195.png",TCI
rtmp://tvcn3sz3vud.rtmphost.com/FMEWebViewer playpath=livestream swfUrl=http://infxapps.influxis.com/apps/jpfssa2ikiocdpy27bde/FMEWebViewer/FMEWebViewer.swf pageUrl=http://www.tci-italia.it/ live=1 timeout=1
#EXTINF:-1 tvg-id="" group-title="LOMBARDIA" tvg-logo="196.png",Super Tv
rtmp://wms.shared.streamshow.it:443/supertv/supertv
#EXTINF:-1 tvg-id="" group-title="LOMBARDIA" tvg-logo="198.png",TeleBoario
rtmp://flash7.streaming.xdevel.com:80/teleboario playpath=teleboario swfUrl=http://share.xdevel.com/api/lib/js/jwplayer5/player.swf pageUrl=http://share.xdevel.com/api/?platform=streamsolution&get=player&key=bdf8d1f1f4ae8b1534d6b59b86931927
#EXTINF:-1 tvg-id="" group-title="LOMBARDIA" tvg-logo="205.png",TeleMilano Piu' Blu
rtmp://ondemand.superstreaming.it:1935/piublu/piublu.sdp

#EXTINF:-1 tvg-id="" group-title="SARDEGNA" tvg-logo="230.png",VideoLina
rtmp://178.33.229.111/live playpath=Videolina swfUrl=http://www.videolina.it/flash/flowplayer.commercial-3.2.11.swf pageUrl=http://www.videolina.it/live.html timeout=120 live=1

#EXTINF:-1 tvg-id="" group-title="SICILIA" tvg-logo="239.png",Radio Tivù Azzurra
rtmp://188.165.224.142:1935/radiotivuazzurralive/radiotivuazzurralive

#EXTINF:-1 tvg-id="" group-title="VARI" tvg-logo="063.png",Automoto TV
rtmp://178.22.187.253:1935/streaming-tv/mp4:myStream3

#EXTINF:-1 tvg-id="" group-title="VARI" tvg-logo="068.png",Rtv San Marino


#EXTINF:-1 tvg-id="" group-title="MUSIC TV" tvg-logo="080.png",FORMUSIC TV
http://sb.top-ix.org:1935/quartaretetv/formusicweb/playlist.m3u8

#EXTINF:-1 tvg-id="" group-title="MUSIC TV" tvg-logo="081.png",Rock TV
mmsh://mediatv2.topix.it/24RockOne66

#EXTINF:-1 tvg-id="" group-title="METEO" tvg-logo="082.png",Il Meteo.It Oggi
http://media.ilmeteo.it/video/iphone/oggi.m4v

#EXTINF:-1 tvg-id="" group-title="METEO" tvg-logo="084.png",Il Meteo.It Domani
http://media.ilmeteo.it/video/iphone/domani.m4v

#EXTINF:-1 tvg-id="" group-title="METEO" tvg-logo="083.png",Il Meteo.It Dopodomani
http://media.ilmeteo.it/video/iphone/dopodomani.m4v

#EXTINF:-1 ,::: CANALI PANDASAT :::
http://www.pandasat.info/iptv/PLAYLIST.avi
#EXTINF:-1 ,Luna Sport
http://vs2.streamcaster.net:1935/test_live/lunasport/playlist.m3u8

#EXTINF:-1 ,Telegenova
http://6zkpywaglbeg.hls.live.5centscdn.com/telegenova/6314e7a3d50e89928d181385c6f3f047.sdp/tracks-v1a1/index.m3u8

#EXTINF:-1,ARENA SPORT 1
http://66.70.129.43:8080/Arena1-SRB/index.m3u8
#EXTINF:-1,ARENA SPORT 2
http://66.70.129.43:8080/Arena2-SRB/index.m3u8
#EXTINF:-1,ARENA SPORT 3
#EXTINF:-1,Sports Tonight
rtmp://freeview.fms.visionip.tv/live/sports_tonight-sports_tonight-live-25f-16x9-SD?ref=www.sportstonightlive.com&USER=STL_0001
#EXTINF:-1,CHELSEA TV
http://ooyalahd2-f.akamaihd.net/i/chelsea02_delivery@145821/master.m3u8

#EXTINF:-1 group-title="CREDITS" tvg-logo="http://i.imgur.com/eGcoFU4.png",[COLOR blue][B]Lista QDR Forum - Visita qdr.forumcommunity.net[/B][/COLOR]
http://bit.ly/qdrforum

#EXT-X-ENDLIST
""".trimIndent()