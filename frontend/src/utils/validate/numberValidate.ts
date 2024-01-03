export function formatNumberWithLeadingZero(number: string): string {
  if (!isNaN(Number(number)) && Number(number) <= 10) {
    return number.padStart(2, '0');
  }

  return number;
}

export function validateInputAttendanceTime(number: string): boolean {
  if (number !== '' && /^[0-9]+$/.test(number)) {
    const newValue = parseInt(number, 10);

    if (!isNaN(newValue) && newValue >= 1 && newValue <= 24) {
      return true;
    }
  }
  return false;
}
