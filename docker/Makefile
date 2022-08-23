

start:
	cd .. & sbt docker:publishLocal
	docker-compose up -d
	docker ps -a

stop:
	docker-compose down
	docker ps -a
