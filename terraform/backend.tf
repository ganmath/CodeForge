
data "aws_ami" "ubuntu" {
  most_recent = true
  owners      = ["099720109477"] # Canonical
  filter {
    name   = "name"
    values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"]
  }
}

resource "aws_key_pair" "kp" {
  key_name   = "codeforge-kp"
  public_key = file(var.public_key_path)
}

resource "aws_security_group" "be_sg" {
  name        = "codeforge-be-sg"
  description = "Allow 8080 and ssh"
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_instance" "backend" {
  ami                    = data.aws_ami.ubuntu.id
  instance_type          = "t3.micro"
  key_name               = aws_key_pair.kp.key_name
  vpc_security_group_ids = [aws_security_group.be_sg.id]

  user_data = <<-EOF
    #!/bin/bash
    apt-get update -y
    apt-get install -y wget unzip
    # Install Java 21
    wget -O- https://apt.corretto.aws/corretto.key | gpg --dearmor | tee /usr/share/keyrings/corretto.gpg > /dev/null
    echo "deb [signed-by=/usr/share/keyrings/corretto.gpg] https://apt.corretto.aws stable main" | tee /etc/apt/sources.list.d/corretto.list
    apt-get update -y && apt-get install -y java-21-amazon-corretto-jdk
    echo "Java installed. Copy your JAR to /opt/codeforge and run: java -jar app.jar"
  EOF

  tags = { Name = "codeforge-backend" }
}

output "backend_public_ip" { value = aws_instance.backend.public_ip }
