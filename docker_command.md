### 1. Uninstall old versions
$ sudo apt-get remove docker docker-engine docker.io containerd runc

### 2. Update the apt package index:
$ sudo apt-get update

### 3. Install packages to allow apt to use a repository over HTTPS:
$ sudo apt-get install \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common
### 4. Add Docker’s official GPG key:

$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -



### 5. 
$ sudo apt-key fingerprint 0EBFCD88

### 6. 
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(. /etc/os-release; echo "$UBUNTU_CODENAME") stable"

### 7. 
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(. /etc/os-release; echo "$UBUNTU_CODENAME") stable"


### 8. sudo apt-get update

### 9. sudo apt-get install docker-ce
=================================================
sudo docker ps

sudo docker image ls

sudo docker rm [container_id]
sudo docker rmi [image_id]


sudo docker run -p 3306:3306 --name mysql_joel -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=joeldatabase -e MYSQL_USER=joel -e MYSQL_PASSWORD=pass -d mysql

sudo docker exec -i -t mysql_joel bash
mysql -u root -p

docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=joel -e POSTGRES_DB=springboot --name postgres_boot -d postgres

============================================
docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=joel -e POSTGRES_DB=springboot --name postgres_boot -d postgres

docker exec -i -t postgres_boot bash

psql -U joel springboot

su - postgres

psql springboot

데이터베이스 조회
\list

테이블 조회
\dt

쿼리
SELECT * FROM account;