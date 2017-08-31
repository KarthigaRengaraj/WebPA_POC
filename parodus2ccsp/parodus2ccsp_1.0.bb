SUMMARY = "parodus2ccsp component"
SECTION = "libs" 
DESCRIPTION = "Webpa client to communicate with parodus in RDK environment"
HOMEPAGE = "https://github.com/Comcast/parodus2ccsp"

LICENSE = "CLOSED"

DEPENDS = "cjson wrp-c wdmp-c trower-base64 nanomsg msgpackc rdk-logger log4c libparodus dbus ccsp-common-library"
#DEPENDS = "cjson msgpackc trower-base64 wrp-c wdmp-c libparodus cimplog rdk-logger log4c dbus ccsp-common-library"

SRCREV = "0ea5c9f4a5ed19b9abe07638e17a240070b6c7c7" 
SRC_URI = "git://github.com/Comcast/parodus2ccsp.git;protocol=https \
	   file://parodus2ccsp_cmake.patch"

SRC_URI[md5sum] = "7ec7e4fe9d7436c8e8bd450a8bf5c7da"
SRC_URI[sha256sum] = "0bbdd1f2345f676418a6cde6504fbeb75df4fd1f9ccaa9a0a068327838431d44"

PV = "git+${SRCPV}"
S = "${WORKDIR}/git"

LDFLAGS += "-lm -llog4c -lrdkloggers -lcjson -lwrp-c -lwdmp-c -lmsgpackc -ltrower-base64 -lnanomsg -llibparodus -ldbus-1 -lccsp_common -lpthread"

CFLAGS_append = " \
    -I${STAGING_INCDIR} \
    -I${STAGING_INCDIR}/cjson \
    -I${STAGING_LIBDIR}/wdmp-c \
    -I${STAGING_INCDIR}/wdmp-c \
    -I${STAGING_INCDIR}/wrp-c \
    -I${STAGING_INCDIR}/cimplog \
    -I${STAGING_INCDIR}/nanomsg \
    -I${STAGING_INCDIR}/trower-base64 \
    -I${STAGING_INCDIR}/libparodus \
    -I${STAGING_INCDIR}/dbus-1.0 \
    -I${STAGING_LIBDIR}/dbus-1.0/include/ \
    -I${STAGING_INCDIR}/ccsp \
    -I${STAGING_INCDIR}/ccsp/linux \
    "
CFLAGS_append += "-DFEATURE_SUPPORT_RDKLOG"
EXTRA_OECMAKE = "-DBUILD_YOCTO=true"

inherit cmake 

do_install_append() {
    install -d ${D}/usr/ccsp/webpa
    install -m 644 ${S}/source/broadband/arch/intel_usg/boards/rdkb_atom/config/comcast/WebpaAgent.xml -t ${D}/usr/ccsp/webpa
}

FILES_${PN} += " \
    ${exec_prefix}/ccsp/webpa \
    ${bindir}/webpa \
    ${libdir}/libbroadband.* \ 
"
INSANE_SKIP_${PN}-dev += "dev-elf"
INSANE_SKIP_${PN} += "dev-deps"
