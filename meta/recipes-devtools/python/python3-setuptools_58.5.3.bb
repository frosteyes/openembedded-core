SUMMARY = "Download, build, install, upgrade, and uninstall Python packages"
HOMEPAGE = "https://pypi.org/project/setuptools"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=1;endline=19;md5=7a7126e068206290f3fe9f8d6c713ea6"

inherit pypi setuptools3

SRC_URI:append:class-native = " file://0001-conditionally-do-not-fetch-code-by-easy_install.patch"

SRC_URI += "\
    file://0001-change-shebang-to-python3.patch \
    file://0001-_distutils-sysconfig-append-STAGING_LIBDIR-python-sy.patch \
"

SRC_URI[sha256sum] = "dae6b934a965c8a59d6d230d3867ec408bb95e73bd538ff77e71fedf1eaca729"

DEPENDS += "${PYTHON_PN}"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-2to3 \
    ${PYTHON_PN}-compile \
    ${PYTHON_PN}-compression \
    ${PYTHON_PN}-ctypes \
    ${PYTHON_PN}-distutils \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-html \
    ${PYTHON_PN}-json \
    ${PYTHON_PN}-netserver \
    ${PYTHON_PN}-numbers \
    ${PYTHON_PN}-pickle \
    ${PYTHON_PN}-pkg-resources \
    ${PYTHON_PN}-pkgutil \
    ${PYTHON_PN}-plistlib \
    ${PYTHON_PN}-shell \
    ${PYTHON_PN}-stringold \
    ${PYTHON_PN}-threading \
    ${PYTHON_PN}-unittest \
    ${PYTHON_PN}-xml \
"

do_install:prepend() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

BBCLASSEXTEND = "native nativesdk"

# The pkg-resources module can be used by itself, without the package downloader
# and easy_install. Ship it in a separate package so that it can be used by
# minimal distributions.
PACKAGES =+ "${PYTHON_PN}-pkg-resources "
FILES:${PYTHON_PN}-pkg-resources = "${PYTHON_SITEPACKAGES_DIR}/pkg_resources/*"
RDEPENDS:${PYTHON_PN}-pkg-resources = "\
    ${PYTHON_PN}-compression \
    ${PYTHON_PN}-email \
    ${PYTHON_PN}-plistlib \
    ${PYTHON_PN}-pprint \
"
