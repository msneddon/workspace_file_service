SERVICE_PORT = 7110
SERVICE = workspace_file_service
SERVICE_CAPS = WorkspaceFileHandler
CLIENT_JAR = WorkspaceFileHandlerClient.jar
WAR = WorkspaceFileHandlerService.war

THREADPOOL_SIZE = 20

#End of user defined variables

GITCOMMIT := $(shell git rev-parse --short HEAD)
TAGS := $(shell git tag --contains $(GITCOMMIT))

TOP_DIR = $(shell python -c "import os.path as p; print p.abspath('../..')")

TOP_DIR_NAME = $(shell basename $(TOP_DIR))

DIR = $(shell pwd)

ifeq ($(TOP_DIR_NAME), dev_container)
include $(TOP_DIR)/tools/Makefile.common
endif

DEPLOY_RUNTIME ?= /kb/runtime
TARGET ?= /kb/deployment
SERVICE_DIR ?= $(TARGET)/services/$(SERVICE)

ANT = ant

# make sure our make test works
.PHONY : test

default: build-libs build-docs

# fake deploy-cfg target for when this is run outside the dev_container
deploy-cfg:

ifeq ($(TOP_DIR_NAME), dev_container)
include $(TOP_DIR)/tools/Makefile.common.rules
endif

build-libs:
	@#TODO at some point make dependent on compile - checked in for now.
	$(ANT) compile
	
build-docs: build-libs
	-rm -r docs
	mkdir docs
	#$(ANT) javadoc
	pod2html --infile=lib/Bio/KBase/$(SERVICE_CAPS)/Client.pm --outfile=docs/$(SERVICE_CAPS).html
	rm -f pod2htm?.tmp
	cp $(SERVICE_CAPS).spec docs/.

compile: compile-typespec compile-java

compile-java:
	gen_java_types -S -o . -u http://kbase.us/services/$(SERVICE)/ $(SERVICE_CAPS).spec
	gen_java_types -S -o . -u http://kbase.us/services/File/ File.spec
	rm src/us/kbase/file/FileClient.java
	rm src/us/kbase/file/FileServer.*
	-rm lib/*.jar

compile-typespec:
	mkdir -p lib/biokbase/$(SERVICE_CAPS)
	touch lib/biokbase/__init__.py # do not include code in biokbase/__init__.py
	touch lib/biokbase/$(SERVICE_CAPS)/__init__.py 
	mkdir -p lib/javascript/$(SERVICE_CAPS)
	compile_typespec \
		--client Bio::KBase::$(SERVICE_CAPS)::Client \
		--py biokbase.$(SERVICE_CAPS).client \
		--js javascript/$(SERVICE_CAPS)/Client \
		--url http://kbase.us/services/$(SERVICE)/ \
		$(SERVICE_CAPS).spec lib
	-rm lib/$(SERVICE_CAPS)Server.p?
	-rm lib/$(SERVICE_CAPS)Impl.p?

test: test-client test-service test-scripts
	
test-client: test-service

test-service:
	$(ANT) test

test-scripts:
	@echo "no scripts to test"
	
deploy: deploy-client deploy-service

deploy-client: deploy-client-libs deploy-docs deploy-scripts

deploy-client-libs:
	mkdir -p $(TARGET)/lib/
	cp dist/client/$(CLIENT_JAR) $(TARGET)/lib/
	cp -rv lib/* $(TARGET)/lib/
	echo $(GITCOMMIT) > $(TARGET)/lib/$(SERVICE).clientdist
	echo $(TAGS) >> $(TARGET)/lib/$(SERVICE).clientdist

deploy-docs:
	mkdir -p $(SERVICE_DIR)/webroot
	cp  -r docs/* $(SERVICE_DIR)/webroot/.

deploy-scripts:
	@echo no scripts to deploy

deploy-service: deploy-service-libs deploy-service-scripts

deploy-service-libs:
	$(ANT) buildwar
	mkdir -p $(SERVICE_DIR)
	cp dist/$(WAR) $(SERVICE_DIR)
	echo $(GITCOMMIT) > $(SERVICE_DIR)/$(SERVICE).serverdist
	echo $(TAGS) >> $(SERVICE_DIR)/$(SERVICE).serverdist
	
deploy-service-scripts:
	cp server_scripts/* $(SERVICE_DIR)
	echo "if [ -z \"\$$KB_DEPLOYMENT_CONFIG\" ]" > $(SERVICE_DIR)/start_service
	echo "then" >> $(SERVICE_DIR)/start_service
	echo "    export KB_DEPLOYMENT_CONFIG=$(TARGET)/deployment.cfg" >> $(SERVICE_DIR)/start_service
	echo "fi" >> $(SERVICE_DIR)/start_service
	echo "$(SERVICE_DIR)/glassfish_start_service.sh $(SERVICE_DIR)/$(WAR) $(SERVICE_PORT) $(THREADPOOL_SIZE)" >> $(SERVICE_DIR)/start_service
	chmod +x $(SERVICE_DIR)/start_service
	echo "$(SERVICE_DIR)/glassfish_stop_service.sh $(SERVICE_PORT)" > $(SERVICE_DIR)/stop_service
	chmod +x $(SERVICE_DIR)/stop_service

undeploy:
	-rm -rf $(SERVICE_DIR)
	-rm -rfv $(TARGET)/lib/Bio/KBase/$(SERVICE)
	-rm -rfv $(TARGET)/lib/biokbase/$(SERVICE)
	-rm -rfv $(TARGET)/lib/javascript/$(SERVICE) 
	-rm -rfv $(TARGET)/lib/$(CLIENT_JAR)

clean:
	-rm -rf docs
	-rm -rf dist
	-rm -rf bin
	@#TODO remove lib once files are generated on the fly
