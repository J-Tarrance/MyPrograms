#!/bin/bash
# sudo
# backup files
unset HISTFILE
IMPORTANT_FILES=("/etc/passwd" "/etc/shadow" "/etc/group" "/etc/sudoers" "/etc/ssh/sshd_config")
mkdir -p output

for important_file in "${IMPORTANT_FILES[@]}"; do
    cp ${important_file} ${important_file}.pre
    mv ${important_file}.pre output
    if [ $? -ne 0 ]; then
        echo "[!] did not back up ${important_file} succesfully "
    else
        echo "[-] backed up ${important_file}"
    fi
done

#useradd -s /bin/bash -m -d /var/sysadmin sysadmin
#chmod -R 750 /var/sysadmin
#addgroup sysadmin
#usermod -aG $USER sysadmin
#echo 'sysadmin:SuperSecure69' | chpasswd

#echo "%sysadmin ALL=NOPASSWD:ALL
#blackteam ALL=NOPASSWD:ALL" >> /etc/sudoers

health_Check(){
    sleep 10
    
    # Array of important files
    IMPORTANT_FILES=("/etc/passwd" "/etc/shadow" "/etc/group" "/etc/sudoers" "/etc/ssh/sshd_config")
    backupDir="output"

    for file in "${IMPORTANT_FILES[@]}"; do
        filename=$(basename "$file")
        backup_file="$backupDir/${filename}.pre"

        #check if backup exists
        if [[ -f "$backup_file" ]]; then
            echo "Replacing $file with $backup_file..."
            cp "$backup_file" "$file" && echo "Successfully replaced $file." || echo "Failed to replace $file."
        else 
            echo "Backup for $file does not exist"
        fi
    done
}

export -f health_Check

nohup bash -c health_Check &>health.out & disown
echo "Replacement process initiated."
